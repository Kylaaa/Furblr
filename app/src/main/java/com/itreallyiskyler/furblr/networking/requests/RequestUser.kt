package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageUserDetails
import com.itreallyiskyler.furblr.util.Promise
import java.lang.Exception

private fun cleanUserId(userId: String) : String {
    return userId.replace("_", "", true).lowercase()
}

class RequestUser(
    val userId : String) : IPageParser<PageUserDetails>,
    BaseRequest(BuildConfig.BASE_URL, "user/${cleanUserId(userId)}/") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any?) : PageUserDetails {
            //println("Successfully fetched : ${this.getUrl()}")
            return PageUserDetails(httpBody as String);
        }
        var failure = fun(message : Any?) {
            // TODO("Not yet implemented")
            println("Failed to parse PageUserDetails : " + (message as Exception).toString())
        }

        return GET().then(success, failure)
    }
}