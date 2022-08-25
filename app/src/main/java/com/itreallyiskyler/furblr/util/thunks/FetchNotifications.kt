package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.networking.models.PageOthers
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.Promise

fun FetchNotifications(
    forceRefresh : Boolean
) : Promise {
    val userIdsToFetch: MutableSet<String> = mutableSetOf()
    val postIdsToFetch: MutableSet<Long> = mutableSetOf()

    return SingletonManager.get().NetworkingManager.requestNotifications().fetchContent()
    .then(fun(pageOtherResponse: Any?): Promise {

        // save all of the notifications
        PersistPageOthers(pageOtherResponse as PageOthers)

        // find which notifications reference posts that need to be pulled
        var allPosts : List<Long> = pageOtherResponse.favorites.map { it.sourcePost!! } +
                pageOtherResponse.comments.map { it.sourcePost!! }
        postIdsToFetch.addAll(allPosts)

        return Promise.resolve(pageOtherResponse)
    }, fun(fetchError: Any?) {
        SingletonManager.get().LoggingManager.getChannel().logError(
            "Failed to fetch and persist notifications with error : $fetchError")
    })
        .then(fun(pageOtherResponse : Any?): Promise {
            // parse the journals
            var fetchPromises = mutableListOf<Promise>()
            val journalIds = (pageOtherResponse as PageOthers).journalIds.map { it.journalId }
            var journalsToFetch: MutableList<Long> = journalIds.toMutableList()

            if (!forceRefresh) {
                val journalDao = SingletonManager.get().DBManager.getDB().journalsDao()
                val existingJournals = journalDao.getExistingJournalsWithIds(journalsToFetch)
                existingJournals.forEach { journalsToFetch.remove(it.id) }
            }

            journalsToFetch.forEach {
                val promise = SingletonManager.get().NetworkingManager.requestJournalDetails(it).fetchContent()
                    .then(fun(journalDetails: Any?) {
                        val details = journalDetails as PageJournalDetails

                        // keep track of usernames to fetch details about
                        userIdsToFetch.add(details.artist)

                        // save the journal data
                        return PersistPageJournalDetails(it, details)
                    }, fun(fetchErr: Any?) {
                        SingletonManager.get().LoggingManager.getChannel().logError(
                            "Failed to fetch and persist journal details (#$it) : $fetchErr")
                    })
                fetchPromises.add(promise)
            }
            return Promise.all(fetchPromises.toTypedArray())
        }, fun(parseError: Any?) {
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Something went wrong parsing the journal ids to fetch : $parseError")
        })
        .then(fun(_: Any?): Promise {
            val dbImpl = SingletonManager.get().DBManager.getDB()
            // figure out which creators we don't have information for
            var missingUserIds = userIdsToFetch
            val users: List<User> =
                dbImpl.usersDao().getExistingUsersForUsernames(userIdsToFetch.toList())
            missingUserIds.removeAll(users.map { it.username })

            var missingViewIds = postIdsToFetch
            val views = dbImpl.viewsDao().getExistingViewsWithIds(missingViewIds.toList())
            missingViewIds.removeAll(views.map { it.id })

            // fetch the creator information
            val fetchPromises = arrayOf<Promise>(
                FetchUsersByUsernames(missingUserIds),
                FetchContentForPostIds(missingViewIds, ContentFeedId.Other)
            )
            return Promise.all(fetchPromises)
        }, fun(userFetchErr: Any?) {
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Failed to fetch user info with err : $userFetchErr")
        })
}