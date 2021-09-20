package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.COMMENTS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.Comment

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateComment(vararg comment:Comment)

    @Query("SELECT * FROM $COMMENTS_TABLE_NAME")
    fun getAllComments(): List<Comment>
}