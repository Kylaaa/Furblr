package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

abstract class ContentManagerInitializer : Initializer<ContentManager> {
    override fun create(context: Context): ContentManager {
        return ContentManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            DBManagerInitializer::class.java,
            LoggingManagerInitializer::class.java,
            NetworkingManagerInitializer::class.java,
        )
    }
}