package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.LoggingChannel

object LoggingManager {
    private val DEFAULT_CHANNEL = LoggingChannel()
    fun setLogLevel(level : LogLevel) {
        DEFAULT_CHANNEL.logLevel = level
    }
    fun log(level: LogLevel, message: Any?) {
        DEFAULT_CHANNEL.log(level, message)
    }

    fun createChannel(
        channelName : String? = null,
        level : LogLevel = LogLevel.NONE,
        handler : ((Any?)->Unit)? = null) : LoggingChannel {
        return LoggingChannel(channelName, level, handler)
    }
}