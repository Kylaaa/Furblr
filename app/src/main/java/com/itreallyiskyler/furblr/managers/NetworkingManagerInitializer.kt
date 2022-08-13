package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

class NetworkingManagerInitializer : Initializer<NetworkingManager> {
    override fun create(context: Context): NetworkingManager {
        return NetworkingManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            LoggingManagerInitializer::class.java
        )
    }
}