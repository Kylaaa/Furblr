package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

class DBManagerInitializer : Initializer<IDBManager> {
    override fun create(context: Context): IDBManager {
        DBManager.init(context)
        //DBManager.get().resetDB()
        return DBManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}