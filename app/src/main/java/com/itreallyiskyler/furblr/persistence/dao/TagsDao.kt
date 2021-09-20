package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.TAGS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.Tag

@Dao
interface TagsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTag(vararg tag: Tag)

    @Query("SELECT * FROM $TAGS_TABLE_NAME")
    fun getAllTags(): List<Tag>
}