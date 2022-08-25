package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

class RequestView(val postId : Long)
    : BaseRequest(BuildConfig.BASE_URL, "view/$postId/") {

    constructor(
        postId: Long,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel
    ) : this(postId) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PagePostDetails {
            return PagePostDetails.parseFromHttp(httpBody as String)
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError("Failed to fetch view details : $message")
        }

        return GET().then(success, failure)
    }
}