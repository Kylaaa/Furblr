package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.enum.CommentLocationId
import com.itreallyiskyler.furblr.persistence.entities.COMMENTS_COLUMN_NAME_HOST_ID
import com.itreallyiskyler.furblr.persistence.entities.COMMENTS_COLUMN_NAME_SOURCE_LOCATION
import com.itreallyiskyler.furblr.persistence.entities.COMMENTS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.Comment

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateComment(vararg comment:Comment)

    @Query("SELECT * FROM $COMMENTS_TABLE_NAME " +
            "WHERE $COMMENTS_COLUMN_NAME_HOST_ID IN (:postIds)" +
            "AND $COMMENTS_COLUMN_NAME_SOURCE_LOCATION = :commentLocation")
    fun getCommentsForPosts(postIds: List<Long>, commentLocation: Int = CommentLocationId.Post.id) : List<Comment>

    @Query("SELECT * FROM $COMMENTS_TABLE_NAME")
    fun getAllComments(): List<Comment>
}