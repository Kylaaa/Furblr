package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer

class AuthManagerInitializer : Initializer<AuthManager> {
    override fun create(context: Context): AuthManager {
        return AuthManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(
            LoggingManagerInitializer::class.java
        )
    }
}