package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

class RequestJournalDetails(journalId : Long) : BaseRequest(BuildConfig.BASE_URL, "journal/$journalId/") {

    constructor(
        journalId: Long,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel = SingletonManager.get().NetworkingManager.logChannel
    ) : this(journalId) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageJournalDetails {
            return PageJournalDetails(httpBody as String)
        }
        var failure = fun(message : Any?) {
            getLogChannel().logError(message as Exception);
        }

        return GET().then(success, failure)
    }
}