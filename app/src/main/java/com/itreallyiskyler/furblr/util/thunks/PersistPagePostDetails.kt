package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistPagePostDetails(
    postId : Long,
    pagePostDetails : PagePostDetails,
    contentFeedId : ContentFeedId) {

    val dbImpl = SingletonManager.get().DBManager.getDB()

    // Add the view to the Views table
    val view = View(
        id = postId,

        // Post Content
        profileId = pagePostDetails.artist,
        title = pagePostDetails.title,
        description = pagePostDetails.description,
        date = pagePostDetails.uploadDate,

        // Content
        contentUrl = pagePostDetails.contentUrl,
        submissionImgUrl = pagePostDetails.thumbnailUrl,
        submissionImgSizeWidth = pagePostDetails.size.first,
        submissionImgSizeHeight = pagePostDetails.size.second,

        // Counts and Favorites
        viewCount = pagePostDetails.totalViews,
        commentCount = pagePostDetails.comments.count().toLong(),
        favoriteCount = pagePostDetails.totalFavorites,
        favKey = pagePostDetails.favoriteKey,
        hasFavorited = pagePostDetails.hasFavorited,

        // Metadata
        rating = pagePostDetails.rating.id,
        kind = pagePostDetails.kind.id,
        category = pagePostDetails.category.id,
        theme = pagePostDetails.theme.id,
        gender = pagePostDetails.gender.id
    )
    dbImpl.viewsDao().insertOrUpdate(view)

    // add each comment to the Comments table
    pagePostDetails.comments.forEach { comment: IPostComment ->
        run {
            val commentEntity = Comment(
                comment.id,
                postId,
                CommentLocationId.Post.id,
                comment.uploaderName,
                comment.content,
                comment.date
            )
            dbImpl.commentsDao().insertOrUpdateComment(commentEntity)
        }
    }

    // prevent duplicate tag insertions
    dbImpl.tagsDao().deleteTagsForPost(postId)

    // Add each tag to the Tags table
    pagePostDetails.tags.forEach { tag: IPostTag ->
        run {
            val tagEntity = Tag(postId, tag.content)
            dbImpl.tagsDao().insertOrUpdateTag(tagEntity)
        }
    }

    // Add the post to the feed
    val feedId = FeedId(
        contentFeedId.id,
        pagePostDetails.kind.id,
        postId,
        pagePostDetails.uploadDate)
    dbImpl.contentFeedDao().insertOrUpdate(feedId)
}