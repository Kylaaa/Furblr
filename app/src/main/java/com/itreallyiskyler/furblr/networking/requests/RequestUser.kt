package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

class RequestUser(
    val userId : String) : IPageParser<PageUserDetails>,
    BaseRequest(BuildConfig.BASE_URL, "user/$userId/") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageUserDetails {
            return PageUserDetails(httpBody as String);
        }
        var failure = fun(message : Any?) {
            TODO("Not yet implemented")
            println(message as Exception);
        }

        return GET().then(success, failure)
    }
}