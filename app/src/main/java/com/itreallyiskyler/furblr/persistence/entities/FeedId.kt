package com.itreallyiskyler.furblr.persistence.entities

import androidx.room.*
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.enum.PostKind

const val FEED_TABLE_NAME = "feedid"
const val FEED_COLUMN_NAME_POST_ID = "postId"
const val FEED_COLUMN_NAME_POST_KIND = "postKind"
const val FEED_COLUMN_NAME_DATE = "date"
const val FEED_COLUMN_NAME_FEED_ID = "feedId"

@Entity(primaryKeys = [FEED_COLUMN_NAME_POST_ID, FEED_COLUMN_NAME_POST_KIND, FEED_COLUMN_NAME_FEED_ID])
data class FeedId(
    @ColumnInfo(name = FEED_COLUMN_NAME_FEED_ID)
    var feed : Int,

    @ColumnInfo(name = FEED_COLUMN_NAME_POST_KIND)
    var postKind : Int,

    @ColumnInfo(name = FEED_COLUMN_NAME_POST_ID)
    var postId : Long,

    @ColumnInfo(name = FEED_COLUMN_NAME_DATE)
    var date : String
){
    companion object {
        fun fromPost(feedId : ContentFeedId, parentPost : View) : FeedId {
            return FeedId(feedId.id, PostKind.Image.id, parentPost.id, parentPost.date);
        }

        fun fromJournal(parentJournal : Journal) : FeedId {
            return FeedId(ContentFeedId.Home.id, PostKind.Journal.id, parentJournal.id, parentJournal.date);
        }
    }
}