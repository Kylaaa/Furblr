package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

// This DAO is specialized for the kinds of queries to populate the Home Page UI

@Dao
interface HomePageDao {
    @Query("SELECT * FROM $POSTS_TABLE_NAME " +
            "ORDER BY datetime($POSTS_COLUMN_NAME_DATE) DESC " +
            "LIMIT :pageSize " +
            "OFFSET :offset")
    fun getHomePagePostsByPage(pageSize : Int = 48, offset : Int = 0): List<Post>

    @Query("SELECT * FROM $TAGS_TABLE_NAME " +
            "WHERE $TAGS_COLUMN_NAME_POST_ID IN (:postIds)")
    fun getTagsForPosts(postIds : List<Long>) : List<Tag>

    @Query("SELECT * FROM $COMMENTS_TABLE_NAME " +
            "WHERE $COMMENTS_COLUMN_NAME_POST_ID IN (:postIds)")
    fun getCommentsForPosts(postIds: List<Long>) : List<Comment>

    @Query("SELECT * FROM $BLACKLISTED_TAGS_TABLE_NAME")
    fun getBlacklistedTags() : List<BlacklistedTag>
}