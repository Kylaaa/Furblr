package com.itreallyiskyler.furblr.util

import kotlin.system.measureTimeMillis

object Profiler {
    fun measure(loggingChannel : LoggingChannel, message : String, callback : ()->Unit) {
        val timeMS = measureTimeMillis(callback)
        loggingChannel.logInfo("$message in $timeMS ms")
    }
}