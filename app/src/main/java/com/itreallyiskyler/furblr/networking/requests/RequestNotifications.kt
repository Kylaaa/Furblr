package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageOthers
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

class RequestNotifications() : IPageParser<PageOthers>,
    BaseRequest(BuildConfig.BASE_URL, "msg/others/") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageOthers {
            return PageOthers(httpBody as String)
        }
        var failure = fun(message : Any?) {
            //TODO("Not yet implemented")
            println(message as Exception);
        }

        return GET().then(success, failure)
    }
}