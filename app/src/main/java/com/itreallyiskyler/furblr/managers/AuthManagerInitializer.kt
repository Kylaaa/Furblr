package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer
import com.itreallyiskyler.furblr.enum.LogLevel

class AuthManagerInitializer : Initializer<IAuthManager> {
    override fun create(context: Context): IAuthManager {
        AuthManager.init(
            loggingChannel = LoggingManager.get().createChannel("Authentication", LogLevel.ERROR)
        )
        return AuthManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            LoggingManagerInitializer::class.java
        )
    }
}