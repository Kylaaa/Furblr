package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.models.PageSubmissions
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class RequestSubmissions(
    val scrollDirection : SubmissionScrollDirection = SubmissionScrollDirection.DEFAULT,
    val pageSize : Int = 48,
    val offsetId : Long? = null) : IPageParser<PageSubmissions>,
    BaseRequest(BuildConfig.BASE_URL, "msg/submissions/") {

    init {
        // If extra information has been passed in, update the url to include extra details
        if (scrollDirection != SubmissionScrollDirection.DEFAULT || offsetId != null) {
            var scrollString :String = scrollDirection.value;
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

    override fun getContent(callback: (PageSubmissions) -> Unit) {
        GET(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message);
            }

            override fun onResponse(call: Call, response: Response) {
                val httpBody : ResponseBody? = response.body
                if (httpBody != null) {
                    val responseString = httpBody.string();
                    callback(PageSubmissions(responseString));
                    httpBody.close()
                }
               else
                {
                    callback(PageSubmissions(""));
                }
            }
        })
    }
}