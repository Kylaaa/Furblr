package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSearch
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.util.Promise

fun FetchPageOfSearch(keyword : String, searchOptions : SearchOptions) : Promise {

    var postIds : List<Long> = emptyList()

    return SingletonManager.get().NetworkingManager.requestSearch(keyword, searchOptions).fetchContent()
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
            val users: List<User> = SingletonManager.get().DBManager.getDB().usersDao()
                .getExistingUsersForUsernames(creatorIds.toList())
            users.forEach { user -> missingUserIds.remove(user.username) }

            // fetch the creator information
            return FetchUsersByUsernames(missingUserIds)
                .then(fun(_: Any?): Any {
                    return searchResults
                }, fun(_: Any?): Promise {
                    return Promise.resolve(searchResults)
                })
        }, fun(fetchError: Any?) {
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Failed to fetch user info with error : $fetchError")
        })

        // next, also figure out which posts to fetch up-to-date information
        .then(fun(pageSearch: Any?): Set<Long> {
            // collect information about the most recent postIds
            return (pageSearch as PageSearch).results.map { submission -> submission.postId }.toSet()

        }, fun(submissionsFetchFailureDetails: Any?): Set<Long> {
            // TODO : Signal that the original fetch failed
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Failed to fetch search with error : $submissionsFetchFailureDetails")
            return emptySet<Long>()
        })

        // Next fetch the most recent data for those submissions
        .then(fun(setOfMissingIds: Any?): Promise {
            // fetch all of the missing data and persist it in local storage
            return FetchContentForPostIds(setOfMissingIds as Set<Long>, ContentFeedId.Home)
        }, fun(missingPostsFetchFailureDetails: Any?) {
            // TODO : handle the error
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Fetching the missing posts threw an error : $missingPostsFetchFailureDetails")
        })

        .then(fun(_ :Any?) : List<HomePageImagePost> {
            return ClobberHomePageImagesById(postIds)
        }, fun(persistenceFailureDetails : Any?) {
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Failed to persist search results : $persistenceFailureDetails")
        })
}
