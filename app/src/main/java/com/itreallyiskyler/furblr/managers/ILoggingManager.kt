package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.LoggingChannel

interface ILoggingManager {
    fun setLogLevel(level : LogLevel)
    fun log(level: LogLevel, message: Any?)
    fun createChannel(channelName : String? = null,
        level : LogLevel = LogLevel.NONE,
        handler : ((Any?)->Unit)? = null) : LoggingChannel
}