package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.RequestSearch
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.Promise
import okhttp3.internal.toImmutableList

fun FetchPageOfSearch(dbImpl : AppDatabase,
                    keyword : String,
                    searchOptions: SearchOptions) : Promise {

    return RequestSearch(keyword, searchOptions).fetchContent()
        // TODO : FIGURE OUT HOW TO COMBINE THIS FETCHING HOME PAGE CONTENT
        .then(fun(pageSearch: Any?): Promise {
            val searchResults = pageSearch as PageSearch

            // first figure out which creators we need to fetch information
            val creatorIds: Set<String> =
                searchResults.results.map { result -> result.creatorName }.toSet()

            // figure out which creators we don't have information for
            val missingUserIds = creatorIds.toMutableSet()
            val users: List<User> =
                dbImpl.usersDao().getExistingUsersForUsernames(creatorIds.toList())
            users.forEach { user -> missingUserIds.remove(user.username) }

            // fetch the creator information
            return FetchUsersByUsernames(dbImpl, missingUserIds)
                .then(fun(_: Any?): Any {
                    return searchResults
                }, fun(_: Any?): Promise {
                    return Promise.resolve(searchResults)
                })
        }, fun(_: Any?) {
            println("Failed to fetch user info!")
        })

        // next, also figure out which posts to fetch up-to-date information
        .then(fun(pageSearch: Any?): Set<Long> {
            // cast the results
            val pageSearch = pageSearch as PageSearch

            // collect information about the most recent postIds
            return pageSearch.results.map { submission -> submission.postId }.toSet()

        }, fun(submissionsFetchFailureDetails: Any?): Set<Long> {
            // TODO : Signal that the original fetch failed
            println(submissionsFetchFailureDetails)
            return emptySet<Long>()
        })

        // Next fetch the most recent data for those submissions
        .then(fun(setOfMissingIds: Any?): Promise {
            // fetch all of the missing data and persist it in local storage
            return FetchContentForPostIds(dbImpl, setOfMissingIds as Set<Long>, ContentFeedId.Home)
        }, fun(missingPostsFetchFailureDetails: Any?) {
            // TODO : handle the error
            println("Fetching the missing posts threw an error : $missingPostsFetchFailureDetails")
        })
}
