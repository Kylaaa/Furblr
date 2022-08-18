package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import com.itreallyiskyler.furblr.util.StringUtils

class RequestUser(val userId : String)
    : BaseRequest(BuildConfig.BASE_URL, "user/${StringUtils.cleanUserId(userId)}/") {

    constructor(
        userId: String,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel = SingletonManager.get().NetworkingManager.logChannel
    ) : this(userId) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageUserDetails {
            return PageUserDetails(userId, httpBody as String);
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError("Failed to parse PageUserDetails : $message")
        }

        return GET().then(success, failure)
    }
}