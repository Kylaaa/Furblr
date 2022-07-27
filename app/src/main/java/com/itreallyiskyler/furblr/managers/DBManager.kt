package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.room.Room
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import kotlin.concurrent.thread

object DBManager {
    private lateinit var contentDB : AppDatabase

    fun initialize(context:Context) {
        contentDB = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "furblr-db"
        ).build()

        // debug
        /*thread {
            contentDB.run {
                clearAllTables()
            }
        }*/
    }

    fun getDB() : AppDatabase {
        return contentDB
    }
}