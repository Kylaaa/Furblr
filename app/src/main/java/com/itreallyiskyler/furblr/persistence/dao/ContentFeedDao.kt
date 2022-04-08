package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface ContentFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg ids : FeedId)

    @Query("SELECT * FROM $FEED_TABLE_NAME " +
            "WHERE $FEED_COLUMN_NAME_FEED_ID IN (:contentFeedIds) " +
            "ORDER BY datetime($FEED_COLUMN_NAME_DATE) DESC " +
            "LIMIT :pageSize " +
            "OFFSET :offset")
    fun getPageFromFeed(contentFeedIds: List<Int>, pageSize: Int = 48, offset: Int = 0) : List<FeedId>

    @Query("SELECT * FROM $FEED_TABLE_NAME " +
            "ORDER BY datetime($FEED_COLUMN_NAME_DATE) DESC")
    fun getAllFeedIds() : List<FeedId>
}