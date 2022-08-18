package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.networking.requests.RequestSearch
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.ui.home.HomePageTextPost
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

fun FetchPageOfSearch(dbImpl : AppDatabase,
                      keyword : String,
                      searchOptions : SearchOptions,
                      requestHandler : RequestHandler = SingletonManager.get().NetworkingManager.requestHandler,
                      loggingChannel : LoggingChannel = SingletonManager.get().NetworkingManager.logChannel) : Promise {

    var postIds : List<Long> = emptyList()

    return RequestSearch(keyword, searchOptions).fetchContent()
        // TODO : FIGURE OUT HOW TO COMBINE THIS FETCHING HOME PAGE CONTENT
        .then(fun(pageSearch: Any?): Promise {
            val searchResults = pageSearch as PageSearch

            // store the ids of the resulting views
            postIds = searchResults.results.map { result -> result.postId }.toList()

            // first figure out which creators we need to fetch information
            val creatorIds: Set<String> =
                searchResults.results.map { result -> result.creatorName }.toSet()

            // figure out which creators we don't have information for
            val missingUserIds = creatorIds.toMutableSet()
            val users: List<User> =
                dbImpl.usersDao().getExistingUsersForUsernames(creatorIds.toList())
            users.forEach { user -> missingUserIds.remove(user.username) }

            // fetch the creator information
            return FetchUsersByUsernames(dbImpl, requestHandler, loggingChannel, missingUserIds)
                .then(fun(_: Any?): Any {
                    return searchResults
                }, fun(_: Any?): Promise {
                    return Promise.resolve(searchResults)
                })
        }, fun(fetchError: Any?) {
            loggingChannel.logError("Failed to fetch user info with error : $fetchError")
        })

        // next, also figure out which posts to fetch up-to-date information
        .then(fun(pageSearch: Any?): Set<Long> {
            // collect information about the most recent postIds
            return (pageSearch as PageSearch).results.map { submission -> submission.postId }.toSet()

        }, fun(submissionsFetchFailureDetails: Any?): Set<Long> {
            // TODO : Signal that the original fetch failed
            loggingChannel.logError("Failed to fetch search with error : $submissionsFetchFailureDetails")
            return emptySet<Long>()
        })

        // Next fetch the most recent data for those submissions
        .then(fun(setOfMissingIds: Any?): Promise {
            // fetch all of the missing data and persist it in local storage
            return FetchContentForPostIds(dbImpl, setOfMissingIds as Set<Long>, ContentFeedId.Home)
        }, fun(missingPostsFetchFailureDetails: Any?) {
            // TODO : handle the error
            loggingChannel.logError("Fetching the missing posts threw an error : $missingPostsFetchFailureDetails")
        })

        .then(fun(_ :Any?) : List<HomePageImagePost> {
            return ClobberHomePageImagesById(dbImpl, postIds)
        }, fun(persistenceFailureDetails : Any?) {
            loggingChannel.logError("Failed to persist search results : $persistenceFailureDetails")
        })
}
