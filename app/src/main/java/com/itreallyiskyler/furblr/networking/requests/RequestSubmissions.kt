package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.networking.models.PageHome
import com.itreallyiskyler.furblr.networking.models.PageSubmissions
import com.itreallyiskyler.furblr.util.Promise
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.lang.Exception

class RequestSubmissions(
    val scrollDirection : SubmissionScrollDirection = SubmissionScrollDirection.DEFAULT,
    val pageSize : Int = 48,
    val offsetId : Long? = null) : IPageParser<PageSubmissions>,
    BaseRequest(BuildConfig.BASE_URL, "msg/submissions/") {

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
        var success = fun(httpBody : Any) : PageSubmissions {
            return PageSubmissions(httpBody as String);
        }
        var failure = fun(message : Any) {
            TODO("Not yet implemented")
            println(message as Exception);
        }

        return GET().then(success, failure)
    }
}