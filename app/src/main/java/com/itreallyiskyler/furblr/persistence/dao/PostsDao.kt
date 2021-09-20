package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.POSTS_COLUMN_NAME_ID
import com.itreallyiskyler.furblr.persistence.entities.POSTS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.Post

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg users: Post)

    @Query("SELECT * FROM $POSTS_TABLE_NAME " +
            "WHERE $POSTS_COLUMN_NAME_ID IN (:postIds)")
    fun getExistingPostsWithIds(postIds : List<Long>) : List<Post>
}