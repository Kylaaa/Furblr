package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.networking.requests.RequestHandler
import com.itreallyiskyler.furblr.util.LoggingChannel

interface INetworkingManager {
    val requestHandler : RequestHandler
    val logChannel : LoggingChannel
}