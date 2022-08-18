package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.persistence.db.AppDatabase

interface IDBManager {
    fun getDB() : AppDatabase
    fun resetDB()
}