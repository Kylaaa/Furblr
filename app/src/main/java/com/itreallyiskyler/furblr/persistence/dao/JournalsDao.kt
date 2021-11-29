package com.itreallyiskyler.furblr.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itreallyiskyler.furblr.persistence.entities.*

@Dao
interface JournalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateJournal(vararg journal:Journal)

    @Query("SELECT * FROM $JOURNALS_TABLE_NAME " +
            "WHERE $JOURNALS_COLUMN_NAME_ID IN (:journalIds)")
    fun getExistingJournalsWithIds(journalIds : List<Long>) : List<Journal>

    @Query("SELECT * FROM $JOURNALS_TABLE_NAME")
    fun getAllJournals(): List<Journal>
}