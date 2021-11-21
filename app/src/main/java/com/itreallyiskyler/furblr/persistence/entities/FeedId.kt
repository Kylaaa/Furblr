package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*
import com.itreallyiskyler.furblr.enum.ContentFeedId

const val FEED_TABLE_NAME = "feedId"
const val FEED_COLUMN_NAME_POST_ID = "postId"
const val FEED_COLUMN_NAME_DATE = "date"
const val FEED_COLUMN_NAME_FEED_ID = "feedId"

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(entity = Post::class,
            parentColumns = arrayOf("$POSTS_COLUMN_NAME_ID"),
            childColumns = arrayOf("$FEED_COLUMN_NAME_POST_ID"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(
        Index(FEED_COLUMN_NAME_POST_ID)
    )
)
data class FeedId(
    val _contentFeedId : Int,
    val _parentPostId : Long,
    val _parentPostDate : String,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = FEED_COLUMN_NAME_POST_ID)
    var postId: Long = _parentPostId

    @ColumnInfo(name = FEED_COLUMN_NAME_FEED_ID)
    var feed: Int = _contentFeedId

    @ColumnInfo(name = FEED_COLUMN_NAME_DATE)
    var date : String = _parentPostDate

    companion object {
        fun fromPost(feedId : ContentFeedId, parentPost : Post) : FeedId {
            return FeedId(feedId.id, parentPost.id, parentPost.date);
        }
    }
}