package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageHome
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.Promise


fun FetchPageOfDiscovery(forceRefresh : Boolean) : Promise {

    // fetch all of the content from the home page
    return SingletonManager.get().NetworkingManager.requestHome().fetchContent()
        .then(fun(pageHome: Any?): Promise {
            val content = pageHome as PageHome

            // first figure out which creators we need to fetch information
            val creatorIds: MutableSet<String> = mutableSetOf()
            creatorIds.addAll(content.recentSubmissions.map { submission -> submission.creatorName })
            creatorIds.addAll(content.recentWritings.map { writing -> writing.creatorName })
            creatorIds.addAll(content.recentMusic.map { music -> music.creatorName })
            //creatorIds.addAll(content.recentCrafting.map { craft -> craft.creatorName })

            // figure out which creators we don't have information for
            val usersDao = SingletonManager.get().DBManager.getDB().usersDao()
            val users: List<User> = usersDao.getExistingUsersForUsernames(creatorIds.toList())
            users.forEach { user -> creatorIds.remove(user.username) }

            // fetch the creator information
            return FetchUsersByUsernames(creatorIds)
                .then(fun(_: Any?): Any {
                    return pageHome
                }, fun(_: Any?): Promise {
                    return Promise.resolve(pageHome)
                })
        }, fun(_: Any?) {
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Failed to fetch user info!")
        })

        // next, also figure out which posts to fetch up-to-date information
        .then(fun(pageHome: Any?): Set<Long> {
            // cast the results
            val content = pageHome as PageHome

            // collect information about the most recent postIds
            val viewIds: MutableSet<Long> = mutableSetOf()
            viewIds.addAll(content.recentSubmissions.map { it.postId })
            viewIds.addAll(content.recentWritings.map { it.postId })
            viewIds.addAll(content.recentMusic.map { it.postId })
            //viewIds.addAll(content.recentCrafting.map { it.postId })

            // check if we have pulled down that content yet
            val existingViews = SingletonManager.get().DBManager.getDB().viewsDao()
                .getExistingViewsWithIds(viewIds.toList())
            existingViews.forEach { post -> viewIds.remove(post.id) }

            // return the set of missing IDs so that we can fetch them in the next step
            return viewIds.toSet()

        }, fun(submissionsFetchFailureDetails: Any?): Set<Long> {
            // TODO : Signal that the original fetch failed
            SingletonManager.get().LoggingManager.getChannel().logError(submissionsFetchFailureDetails)
            return emptySet<Long>()
        })

        // Next fetch the most recent data for those submissions
        .then(fun(setOfMissingIds: Any?): Promise {
            // fetch all of the missing data and persist it in local storage
            return FetchContentForPostIds(setOfMissingIds as Set<Long>, ContentFeedId.Discover)
        }, fun(missingPostsFetchFailureDetails: Any?) {
            // TODO : handle the error
            SingletonManager.get().LoggingManager.getChannel().logError(
                "Fetching the missing posts threw an error : $missingPostsFetchFailureDetails")
        })
}