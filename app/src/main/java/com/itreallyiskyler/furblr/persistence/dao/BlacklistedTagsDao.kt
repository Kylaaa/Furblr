package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface BlacklistedTagsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTag(vararg blacklistedTag: BlacklistedTag)

    @Query("SELECT DISTINCT $TAGS_COLUMN_NAME_POST_ID FROM $TAGS_TABLE_NAME " +
            "WHERE $TAGS_COLUMN_NAME_POST_ID IN (:postIds) " +
            "AND $TAGS_COLUMN_NAME_CONTENTS NOT IN " +
            "(SELECT $BLACKLISTED_TAGS_COLUMN_NAME_CONTENTS FROM $BLACKLISTED_TAGS_TABLE_NAME)")
    fun getUnblacklistedIdsForPosts(postIds : List<Long>) : List<Long>

    @Query("SELECT * FROM $BLACKLISTED_TAGS_TABLE_NAME")
    fun getAllBlacklistedTags(): List<BlacklistedTag>
}