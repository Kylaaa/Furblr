package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.LoggingChannel

class LoggingManager : ILoggingManager {
    private val DEFAULT_CHANNEL = LoggingChannel()
    override fun setLogLevel(level : LogLevel) {
        DEFAULT_CHANNEL.logLevel = level
    }
    override fun log(level: LogLevel, message: Any?) {
        DEFAULT_CHANNEL.log(level, message)
    }

    override fun profile(message: String, callback: () -> Unit) {
        DEFAULT_CHANNEL.profile(message, callback)
    }

    override fun createChannel(
        channelName : String?,
        level : LogLevel,
        handler : ((Any?)->Unit)?) : LoggingChannel {
        return LoggingChannel(channelName, level, handler)
    }

    companion object : IManagerAccessor<LoggingManager> {
        private lateinit var instance : LoggingManager
        override fun get(): LoggingManager {
            return instance
        }
        fun init() {
            instance = LoggingManager()
        }
    }
}