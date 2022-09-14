package com.itreallyiskyler.testhelpers.managers

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.itreallyiskyler.furblr.managers.IDBManager
import com.itreallyiskyler.furblr.persistence.db.AppDatabase

open class MockDBManager : IDBManager {
    val context = ApplicationProvider.getApplicationContext<Context>()
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "furblr-testing-db"
    ).build()

    override fun getDB(): AppDatabase {
        return db
    }
    override fun resetDB() {
        db.clearAllTables()
    }
}