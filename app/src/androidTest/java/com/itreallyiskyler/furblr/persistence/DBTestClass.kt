package com.itreallyiskyler.furblr.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import org.junit.After
import org.junit.Before

open class DBTestClass {
    private lateinit var db : AppDatabase

    protected fun getDB() : AppDatabase { return db }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "furblr-testing-db"
        ).build()
    }

    @After
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }
}