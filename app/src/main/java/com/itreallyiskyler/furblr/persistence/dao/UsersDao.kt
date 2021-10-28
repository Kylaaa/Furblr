package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.USERS_COLUMN_NAME_ID
import com.itreallyiskyler.furblr.persistence.entities.USERS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.User

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateUsers(vararg users:User)

    @Query("SELECT * FROM $USERS_TABLE_NAME " +
            "WHERE $USERS_COLUMN_NAME_ID IN (:usernames)")
    fun getExistingUsersForUsernames(usernames : List<String>) : List<User>

    @Query("SELECT * FROM $USERS_TABLE_NAME")
    fun getAllUsers(): List<User>
}