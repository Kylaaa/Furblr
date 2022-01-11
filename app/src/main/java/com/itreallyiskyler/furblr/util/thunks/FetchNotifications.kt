package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.networking.models.PageOthers
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.requests.RequestJournalDetails
import com.itreallyiskyler.furblr.networking.requests.RequestNotifications
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.Promise

fun FetchNotifications(dbImpl : AppDatabase, forceRefresh : Boolean) : Promise {
    val userIdsToFetch: MutableSet<String> = mutableSetOf()
    val postIdsToFetch: MutableSet<Long> = mutableSetOf()

    return RequestNotifications().fetchContent().then(fun(pageOtherResponse: Any?): Promise {
        // save all of the notifications
        PersistPageOthers(dbImpl, pageOtherResponse as PageOthers)

        // find which notifications reference posts that need to be pulled
        var allPosts : List<Long> = pageOtherResponse.Favorites.map { it.sourcePost!! } + pageOtherResponse.Comments.map { it.sourcePost!! }
        postIdsToFetch.addAll(allPosts)

        return Promise.resolve(pageOtherResponse)
    }, fun(fetchError: Any?) {
        println(fetchError)
    })
        .then(fun(pageOtherResponse : Any?): Promise {
            // parse the journals
            var fetchPromises = mutableListOf<Promise>()
            val journalIds = (pageOtherResponse as PageOthers).JournalIds.map { it.journalId }
            var journalsToFetch: MutableList<Long> = journalIds.toMutableList()

            if (!forceRefresh) {
                val existingJournals = dbImpl.journalsDao().getExistingJournalsWithIds(journalsToFetch)
                existingJournals.forEach { journalsToFetch.remove(it.id) }
            }

            journalsToFetch.forEach {
                val promise = RequestJournalDetails(it).fetchContent().then(fun(journalDetails: Any?) {
                    val details = journalDetails as PageJournalDetails

                    // keep track of usernames to fetch details about
                    userIdsToFetch.add(details.Artist)

                    // save the journal data
                    return PersistPageJournalDetails(dbImpl, it, details)
                }, fun(fetchErr: Any?) {
                    println(fetchErr)
                })
                fetchPromises.add(promise)
            }
            return Promise.all(fetchPromises.toTypedArray())
        }, fun(parseError: Any?) {
            println(parseError)
        })
        .then(fun(_: Any?): Promise {
            // figure out which creators we don't have information for
            var missingUserIds = userIdsToFetch
            val users: List<User> =
                dbImpl.usersDao().getExistingUsersForUsernames(userIdsToFetch.toList())
            missingUserIds.removeAll(users.map { it.username })

            var missingPostIds = postIdsToFetch
            val posts = dbImpl.postsDao().getExistingPostsWithIds(missingPostIds.toList())
            missingPostIds.removeAll(posts.map { it.id })

            // fetch the creator information
            val fetchPromises = arrayOf<Promise>(
                FetchUsersByUsernames(dbImpl, missingUserIds),
                FetchContentForPostIds(dbImpl, missingPostIds, ContentFeedId.Other))
            return Promise.all(fetchPromises)
        }, fun(_: Any?) {
            println("Failed to fetch user info!")
        })
}