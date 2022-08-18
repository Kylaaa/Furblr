package com.itreallyiskyler.furblr.managers

import android.content.Context
import androidx.startup.Initializer
import com.itreallyiskyler.furblr.enum.LogLevel

class LoggingManagerInitializer : Initializer<LoggingManager> {
    override fun create(context: Context): LoggingManager {
        LoggingManager.init()
        LoggingManager.get().setLogLevel(LogLevel.INFORMATION)
        return LoggingManager.get()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}