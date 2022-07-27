package com.itreallyiskyler.furblr.util

import com.itreallyiskyler.furblr.enum.LogLevel
import java.lang.IndexOutOfBoundsException

class LoggingChannel(
    channelName : String? = null,
    level : LogLevel = LogLevel.NONE,
    handler : ((Any?)->Unit)? = null) {

    private var channelPrefix : String = if (channelName != null) ("$channelName : ") else ""
    var logLevel : LogLevel = level
    val messageHandler : (Any?)->Unit = handler ?: { message ->
        println("$channelPrefix$message")
    }

    inline fun log(level: LogLevel, message: Any?) {
        if (level == LogLevel.NONE) {
            throw IndexOutOfBoundsException("LogLevel cannot be None")
        }
        if (level.value >= logLevel.value) {
            messageHandler(message)
        }
    }
    fun logTrace(message : Any?) {
        log(LogLevel.TRACE, message)
    }
    fun logInfo(message : Any?) {
        log(LogLevel.INFORMATION, message)
    }
    fun logWarning(message: Any?) {
        log(LogLevel.WARNING, message)
    }
    fun logError(message : Any?) {
        log(LogLevel.ERROR, message)
    }
}