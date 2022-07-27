package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer
import com.itreallyiskyler.furblr.enum.LogLevel

abstract class LoggingManagerInitializer : Initializer<LoggingManager> {
    override fun create(context: Context): LoggingManager {
        LoggingManager.setLogLevel(LogLevel.INFORMATION)
        return LoggingManager
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}