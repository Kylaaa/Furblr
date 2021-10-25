package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.util.Promise
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.lang.Exception

class RequestView(
    val postId : Long) : IPageParser<PagePostDetails>,
    BaseRequest(BuildConfig.BASE_URL, "view/$postId/") {

    override fun fetchContent() : Promise {
        var success = fun(httpBody : Any) : PagePostDetails {
            return PagePostDetails(httpBody as String);
        }
        var failure = fun(message : Any) {
            TODO("Not yet implemented")
            println(message as Exception);
        }

        return GET().then(success, failure)
    }
}