package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer
import com.itreallyiskyler.furblr.enum.LogLevel

class NetworkingManagerInitializer : Initializer<NetworkingManager> {
    override fun create(context: Context): NetworkingManager {
        NetworkingManager.init(
            logChannel = LoggingManager.get().createChannel("Networking", LogLevel.INFORMATION)
        )
        return NetworkingManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            LoggingManagerInitializer::class.java
        )
    }
}