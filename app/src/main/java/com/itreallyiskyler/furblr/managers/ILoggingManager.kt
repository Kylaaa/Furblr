package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.LoggingChannel
import kotlin.system.measureTimeMillis

interface ILoggingManager {
    fun setLogLevel(level : LogLevel)
    fun log(level: LogLevel, message: Any?)
    fun profile(message : String, callback : ()->Unit)
    fun createChannel(channelName : String? = null,
        level : LogLevel = LogLevel.NONE,
        handler : ((Any?)->Unit)? = null) : LoggingChannel
}