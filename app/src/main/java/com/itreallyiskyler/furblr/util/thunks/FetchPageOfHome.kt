package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.networking.requests.RequestSubmissions
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.FeedId
import com.itreallyiskyler.furblr.persistence.entities.User
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import okhttp3.internal.toImmutableList


fun FetchPageOfHome(
    dbImpl : AppDatabase,
    requestHandler: RequestHandler,
    loggingChannel: LoggingChannel = SingletonManager.get().NetworkingManager.logChannel,
    page : Int = 0,
    pageSize : Int = 48,
    forceRefresh : Boolean) : Promise {

    val foundHomePageIds: MutableList<Long> = mutableListOf()
    val fetchLastIdInSet = fun(page: Int, pageSize: Int): Long? {
        val posts = dbImpl.contentFeedDao().getPageFromFeed(listOf(ContentFeedId.Home.id), pageSize, page * pageSize)
        val lastItem : FeedId? = posts.findLast { feedId -> feedId.postKind == PostKind.Image.id }
        return lastItem?.postId
    }
    val previousPostId: Long? = if (page == 0) null else fetchLastIdInSet(page, pageSize)

    // fetch all of the content from the submissions list
    return RequestSubmissions(
        SubmissionScrollDirection.DEFAULT,
        pageSize,
        previousPostId,
        requestHandler,
        loggingChannel
    ).fetchContent()

        .then(fun(pageSubmissions: Any?): Promise {
            val submissions = pageSubmissions as PageSubmissions

            // first figure out which creators we need to fetch information
            val creatorIds: Set<String> =
                submissions.Submissions.map { submission -> submission.creatorName }.toSet()

            // figure out which creators we don't have information for
            val missingUserIds = creatorIds.toMutableSet()
            val users: List<User> =
                dbImpl.usersDao().getExistingUsersForUsernames(creatorIds.toList())
            users.forEach { user -> missingUserIds.remove(user.username) }

            // fetch the creator information
            return FetchUsersByUsernames(dbImpl,requestHandler, loggingChannel, missingUserIds)
                .then(fun(_: Any?): Any {
                    return pageSubmissions
                }, fun(_: Any?): Promise {
                    return Promise.resolve(pageSubmissions)
                })
        }, fun(ex: Any?) {
            loggingChannel.logError("Failed to fetch user info! $ex")
        })

        // next, also figure out which posts to fetch up-to-date information
        .then(fun(pageSubmissions: Any?): Set<Long> {
            // cast the results
            val submissions = pageSubmissions as PageSubmissions

            // collect information about the most recent postIds
            submissions.Submissions.forEach { submission -> foundHomePageIds.add(submission.postId) }

            if (forceRefresh) {
                // fetch all of the data regardless of whether
                // we have the information locally already
                return foundHomePageIds.toSet()
            }

            // check if we have pulled down that content yet
            val missingIds = foundHomePageIds.toMutableSet()
            val existingPosts = dbImpl.viewsDao()
                .getExistingViewsWithIds(foundHomePageIds.toImmutableList())
            existingPosts.forEach { post -> missingIds.remove(post.id) }

            // return the set of missing IDs so that we can fetch them in the next step
            return missingIds.toSet()

        }, fun(submissionsFetchFailureDetails: Any?): Set<Long> {
            // TODO : Signal that the original fetch failed
            loggingChannel.logError("Failed to fetch submissions : $submissionsFetchFailureDetails")
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
}