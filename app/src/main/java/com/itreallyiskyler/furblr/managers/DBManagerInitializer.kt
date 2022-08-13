package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

class DBManagerInitializer : Initializer<DBManager> {
    override fun create(context: Context): DBManager {
        DBManager.initialize(context)
        return DBManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}