package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

// This DAO is specialized for the kinds of queries to populate the Home Page UI

@Dao
interface ProfilePageDao {
    @Query("SELECT * FROM $USERS_TABLE_NAME " +
            "WHERE $USERS_COLUMN_NAME_ID LIKE :username ")
    fun getProfileForUser(username: String) : User

    @Query("SELECT * FROM $VIEW_TABLE_NAME " +
            "WHERE $VIEW_COLUMN_NAME_PROFILE_ID LIKE :username " +
            "ORDER BY datetime($VIEW_COLUMN_NAME_DATE) DESC")
    fun getPostsByUser(username : String) : List<View>
}