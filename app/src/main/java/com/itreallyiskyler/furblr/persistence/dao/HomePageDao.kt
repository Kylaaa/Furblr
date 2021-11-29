package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.enum.ContentFeedId
import com.itreallyiskyler.furblr.persistence.entities.*

// This DAO is specialized for the kinds of queries to populate the Home Page UI

@Dao
interface HomePageDao {
    @Query("SELECT DISTINCT $FEED_COLUMN_NAME_POST_ID FROM $FEED_TABLE_NAME " +
            "WHERE $FEED_COLUMN_NAME_FEED_ID = :feedId " +
            "ORDER BY datetime($FEED_COLUMN_NAME_DATE) DESC " +
            "LIMIT :pageSize " +
            "OFFSET :offset")
    fun getHomePagePostIdsByPage(pageSize : Int = 48,
                               offset : Int = 0,
                               feedId: Int = ContentFeedId.Home.id): List<Long>




}