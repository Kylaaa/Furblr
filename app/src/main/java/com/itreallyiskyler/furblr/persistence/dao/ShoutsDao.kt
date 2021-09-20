package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.SHOUTS_TABLE_NAME
import com.itreallyiskyler.furblr.persistence.entities.Shout

@Dao
interface ShoutsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateShout(vararg shout:Shout)

    @Query("SELECT * FROM $SHOUTS_TABLE_NAME")
    fun getAllShouts(): List<Shout>
}