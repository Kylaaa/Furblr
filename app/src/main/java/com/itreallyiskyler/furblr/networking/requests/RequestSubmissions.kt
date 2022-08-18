package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

class RequestSubmissions(
    val scrollDirection : SubmissionScrollDirection = SubmissionScrollDirection.DEFAULT,
    val pageSize : Int = 48,
    val offsetId : Long? = null
) : BaseRequest(BuildConfig.BASE_URL, "msg/submissions/") {

    constructor(
        scrollDirection: SubmissionScrollDirection,
        pageSize: Int,
        offsetId: Long?,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel = SingletonManager.get().NetworkingManager.logChannel
    ) : this(scrollDirection, pageSize, offsetId) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    init {
        // If extra information has been passed in, update the url to include extra details
        if (scrollDirection != SubmissionScrollDirection.DEFAULT || offsetId != null) {
            var scrollString : String = scrollDirection.value;
            if (scrollDirection == SubmissionScrollDirection.DEFAULT) {
                scrollString = SubmissionScrollDirection.NEWEST.value;
            }
            var offsetString = ""
            if (offsetId != null) {
                offsetString = "~$offsetId"
            }
            var updatedPath = getPath() + "$scrollString$offsetString@$pageSize/"
            setPath(updatedPath)
        }
    }

    override fun fetchContent() : Promise {
        val success = fun(httpBody : Any?) : PageSubmissions {
            return PageSubmissions(httpBody as String);
        }
        val failure = fun(message : Any?) {
            getLogChannel().logError(message as Exception);
        }

        return GET().then(success, failure)
    }
}