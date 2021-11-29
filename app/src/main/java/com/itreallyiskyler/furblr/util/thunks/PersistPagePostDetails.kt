package com.itreallyiskyler.furblr.util.thunks

import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind
import com.itreallyiskyler.furblr.networking.models.IPostComment
import com.itreallyiskyler.furblr.networking.models.IPostTag
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.furblr.persistence.entities.Comment
import com.itreallyiskyler.furblr.persistence.entities.FeedId
import com.itreallyiskyler.furblr.persistence.entities.Post
import com.itreallyiskyler.furblr.persistence.entities.Tag

fun PersistPagePostDetails (dbImpl: AppDatabase,
                            postId : Long,
                            pagePostDetails : PagePostDetails,
                            contentFeedId : ContentFeedId) {
    // Add the post to the Posts table
    val post = Post(
        postId,
        pagePostDetails.Artist,
        pagePostDetails.Title,
        pagePostDetails.Description,
        pagePostDetails.ContentUrl,
        pagePostDetails.TotalViews,
        pagePostDetails.Comments.count().toLong(),
        pagePostDetails.TotalFavorites,
        pagePostDetails.Rating.toString(),
        pagePostDetails.FavoriteKey,
        pagePostDetails.HasFavorited,
        pagePostDetails.UploadDate
    )
    dbImpl.postsDao().insertOrUpdate(post)

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