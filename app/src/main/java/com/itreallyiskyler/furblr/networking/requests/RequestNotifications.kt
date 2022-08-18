package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageOthers
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

class RequestNotifications() : BaseRequest(BuildConfig.BASE_URL, "msg/others/") {

    constructor(
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel = SingletonManager.get().NetworkingManager.logChannel
    ) : this() {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageOthers {
            return PageOthers(httpBody as String)
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError("Failed to fetch Notifications : $message")
        }

        return GET().then(success, failure)
    }
}