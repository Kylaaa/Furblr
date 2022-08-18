package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer
import com.itreallyiskyler.furblr.enum.LogLevel

class ContentManagerInitializer : Initializer<ContentManager> {
    override fun create(context: Context): ContentManager {
        ContentManager.init(
            db = DBManager.get().getDB(),
            requestHandler = NetworkingManager.get().requestHandler,
            loggingChannel = LoggingManager.get().createChannel("Content Manager", LogLevel.ERROR)
        )
        return ContentManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            DBManagerInitializer::class.java,
            LoggingManagerInitializer::class.java,
            NetworkingManagerInitializer::class.java,
        )
    }
}