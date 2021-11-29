package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePageImagePost
import com.itreallyiskyler.furblr.util.Promise

fun FetchLatestForPost(dbImpl : AppDatabase,
                       postId : Long,
                       fetchReason : ContentFeedId) : Promise {
    return FetchContentForPostIds(dbImpl, setOf<Long>(postId), fetchReason)

        // Next clobber all of the data from those postIds into content for the HomePage
        .then(fun(_: Any?): List<HomePageImagePost> {
            return ClobberHomePageImagesById(dbImpl, listOf<Long>(postId))
        }, fun(fetchContentFailureDetails: Any?): List<Long> {
            // TODO : handle the error
            return emptyList<Long>()
        })
}