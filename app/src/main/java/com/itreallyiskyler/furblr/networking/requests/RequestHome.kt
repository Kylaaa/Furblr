package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageHome
import com.itreallyiskyler.furblr.util.Promise

class RequestHome : IPageParser<PageHome>,
    BaseRequest(BuildConfig.BASE_URL, "") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageHome {
            return PageHome(httpBody as String);
        }
        var failure = fun(message : Any?) {
            println("Failed to fetch and parse the home page : $message")
        }

        return GET().then(success, failure)
    }
}