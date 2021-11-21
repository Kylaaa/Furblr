package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.models.ThumbnailSubmission
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistFeedIds(dbImpl: AppDatabase,
                   postIds : List<Long>,
                   feedId : ContentFeedId) {
    // save all the ids for each specific content feed
    val posts = dbImpl.postsDao().getExistingPostsWithIds(postIds)
    posts.forEach {
        run {
            val feedId = FeedId.fromPost(feedId, it)
            dbImpl.contentFeedDao().insertOrUpdate(feedId)
        }
    }
}