package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface ViewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg views: View)

    @Query("SELECT * FROM $VIEW_TABLE_NAME " +
            "WHERE $VIEW_COLUMN_NAME_ID IN (:viewIds)")
    fun getExistingViewsWithIds(viewIds : List<Long>) : List<View>
}