package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.LoggingChannel

interface ILoggingManager {
    fun getChannel() : LoggingChannel
    fun createChannel(channelName : String? = null,
        level : LogLevel = LogLevel.NONE,
        handler : ((Any?)->Unit)? = null) : LoggingChannel
}