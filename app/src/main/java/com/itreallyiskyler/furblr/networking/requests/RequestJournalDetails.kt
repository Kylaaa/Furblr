package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageJournalDetails
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

class RequestJournalDetails(journalId : Long) : IPageParser<PageJournalDetails>,
    BaseRequest(BuildConfig.BASE_URL, "journal/$journalId/") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageJournalDetails {
            return PageJournalDetails(httpBody as String)
        }
        var failure = fun(message : Any?) {
            //TODO("Not yet implemented")
            println(message as Exception);
        }

        return GET().then(success, failure)
    }
}