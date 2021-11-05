package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.ui.home.HomePagePost
import com.itreallyiskyler.furblr.util.Promise

fun FetchLatestForPost(dbImpl : AppDatabase,
                       postId : Long) : Promise {
    return FetchContentForPostIds(dbImpl, setOf<Long>(postId))

        // Next clobber all of the data from those postIds into content for the HomePage
        .then(fun(contentViews: Any?): List<HomePagePost> {
            return ClobberHomePageContentById(dbImpl, listOf<Long>(postId))
        }, fun(fetchContentFailureDetails: Any?): List<Long> {
            // TODO : handle the error
            return emptyList<Long>()
        })
}