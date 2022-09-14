package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

class SingletonManagerInitializer : Initializer<SingletonManager> {
    override fun create(context: Context): SingletonManager {
        SingletonManager.init(
            am = AuthManager.get(),
            cm = ContentManager.get(),
            dbm = DBManager.get(),
            lm = LoggingManager.get(),
            nm = NetworkingManager.get())
        return SingletonManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            AuthManagerInitializer::class.java,
            ContentManagerInitializer::class.java,
            DBManagerInitializer::class.java,
            LoggingManagerInitializer::class.java,
            NetworkingManagerInitializer::class.java,
        )
    }
}