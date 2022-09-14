package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.util.Promise

fun FetchLatestForPost(postId : Long, fetchReason : ContentFeedId) : Promise {
    return FetchContentForPostIds(setOf<Long>(postId), fetchReason)

        // Next clobber all of the data from those postIds into content for the HomePage
        .then(fun(_: Any?): List<HomePageImagePost> {
            return ClobberHomePageImagesById(listOf<Long>(postId))
        }, fun(fetchContentFailureDetails: Any?): List<Long> {
            // TODO : handle the error
            SingletonManager.get().LoggingManager.getChannel().logError(fetchContentFailureDetails)
            return emptyList<Long>()
        })
}