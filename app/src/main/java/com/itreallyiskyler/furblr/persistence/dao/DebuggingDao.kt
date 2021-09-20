package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface DebuggingDao {
    @Query("SELECT * FROM $BLACKLISTED_TAGS_TABLE_NAME")
    fun DEBUG_getAllBlacklistedTags(): List<BlacklistedTag>

    @Query("SELECT * FROM $COMMENTS_TABLE_NAME")
    fun DEBUG_getAllComments(): List<Comment>

    @Query("SELECT * FROM $POSTS_TABLE_NAME")
    fun DEBUG_getAllPosts(): List<Post>

    @Query("SELECT * FROM $SHOUTS_TABLE_NAME")
    fun DEBUG_getAllShouts(): List<Shout>

    @Query("SELECT * FROM $TAGS_TABLE_NAME")
    fun DEBUG_getAllTags(): List<Tag>

    @Query("SELECT * FROM $USERS_TABLE_NAME")
    fun DEBUG_getAllUsers(): List<User>
}