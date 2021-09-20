package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.BLACKLISTED_TAGS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.BlacklistedTag

@Dao
interface BlacklistedTagsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTag(vararg blacklistedTag: BlacklistedTag)

    @Query("SELECT * FROM $BLACKLISTED_TAGS_TABLE_NAME")
    fun getAllBlacklistedTags(): List<BlacklistedTag>
}