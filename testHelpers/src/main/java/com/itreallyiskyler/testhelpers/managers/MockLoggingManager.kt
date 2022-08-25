package com.itreallyiskyler.testhelpers.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.managers.ILoggingManager
import com.itreallyiskyler.furblr.util.LoggingChannel

open class MockLoggingManager : ILoggingManager {
    val DEFAULT_LOGGING_CHANNEL : LoggingChannel = LoggingChannel("DEFAULT_LOGGING_CHANNEL", LogLevel.NONE) {}

    override fun createChannel(channelName: String?, level: LogLevel, handler: ((Any?) -> Unit)?): LoggingChannel {
        return LoggingChannel(channelName, LogLevel.NONE, handler)
    }

    override fun getChannel(): LoggingChannel {
        return DEFAULT_LOGGING_CHANNEL
    }
}