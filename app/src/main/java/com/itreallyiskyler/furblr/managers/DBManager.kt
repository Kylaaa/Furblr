package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.room.Room
import com.itreallyiskyler.furblr.persistence.db.AppDatabase
import kotlin.concurrent.thread

class DBManager(context:Context) : IDBManager {
    private val contentDB : AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "furblr-db"
    ).fallbackToDestructiveMigration()
        .build()

    override fun getDB() : AppDatabase {
        return contentDB
    }

    override fun resetDB() {
        thread {
            contentDB.run {
                clearAllTables()
            }
        }
    }

    companion object : IManagerAccessor<DBManager> {
        private lateinit var instance : DBManager
        override fun get(): DBManager {
            return instance
        }
        fun init(context:Context) {
            instance = DBManager(
                context = context
            )
        }
    }
}