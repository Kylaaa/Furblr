package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageHome
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

class RequestHome() : BaseRequest(BuildConfig.BASE_URL, "") {

    constructor(
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel
    ) : this() {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageHome {
            return PageHome.parseFromHttp(httpBody as String);
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError("Failed to fetch and parse the home page : $message")
        }

        return GET().then(success, failure)
    }
}