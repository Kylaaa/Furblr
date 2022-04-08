package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.*

fun PersistPagePostDetails (dbImpl: AppDatabase,
                            postId : Long,
                            pagePostDetails : PagePostDetails,
                            contentFeedId : ContentFeedId) {
    // Add the view to the Views table
    val view = View(
        id = postId,
        profileId = pagePostDetails.Artist,
        title = pagePostDetails.Title,
        description = pagePostDetails.Description,
        date = pagePostDetails.UploadDate,
        rating = pagePostDetails.Rating.toString(),
        submissionImgUrl = pagePostDetails.ContentUrl,
        viewCount = pagePostDetails.TotalViews,
        commentCount = pagePostDetails.Comments.count().toLong(),
        favoriteCount = pagePostDetails.TotalFavorites,
        favKey = pagePostDetails.FavoriteKey,
        hasFavorited = pagePostDetails.HasFavorited,
        viewKind = PostKind.Image.id,
        fileType = null,
        filePreview = null
    )
    dbImpl.viewsDao().insertOrUpdate(view)

    // add each comment to the Comments table
    pagePostDetails.Comments.forEach { comment: IPostComment ->
        run {
            val commentEntity = Comment(
                comment.Id,
                postId,
                CommentLocationId.Post.id,
                comment.UploaderName,
                comment.Content,
                comment.Date
            )
            dbImpl.commentsDao().insertOrUpdateComment(commentEntity)
        }
    }

    // prevent duplicate tag insertions
    dbImpl.tagsDao().deleteTagsForPost(postId)

    // Add each tag to the Tags table
    pagePostDetails.Tags.forEach { tag: IPostTag ->
        run {
            val tagEntity = Tag(postId, tag.Content)
            dbImpl.tagsDao().insertOrUpdateTag(tagEntity)
        }
    }

    // Add the post to the feed
    val feedId = FeedId(contentFeedId.id, PostKind.Image.id, postId, pagePostDetails.UploadDate)
    dbImpl.contentFeedDao().insertOrUpdate(feedId)
}