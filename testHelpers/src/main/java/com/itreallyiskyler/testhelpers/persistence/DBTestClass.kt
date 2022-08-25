package com.itreallyiskyler.testhelpers.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import com.itreallyiskyler.testhelpers.managers.SingletonInitializer
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass

open class DBTestClass() {
    protected fun getDB() : AppDatabase { return SingletonManager.get().DBManager.getDB() }

    companion object {
        @BeforeClass
        @JvmStatic
        fun initDB() {
            SingletonInitializer.init()
        }

        @AfterClass
        @JvmStatic
        fun closeDB() {
            SingletonManager.get().DBManager.getDB().close()
        }
    }


    @After
    fun cleanDb() {
        SingletonManager.get().DBManager.getDB().clearAllTables()
    }
}