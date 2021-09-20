package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PagePostDetails
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class RequestView(
    val postId : Long) : IPageParser<PagePostDetails>,
    BaseRequest(BuildConfig.BASE_URL, "view/$postId/") {

    override fun getContent(callback: (PagePostDetails) -> Unit) {
        GET(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.message);
            }

            override fun onResponse(call: Call, response: Response) {
                val httpBody : ResponseBody? = response.body
                if (httpBody != null) {
                    val responseString = httpBody.string();
                    callback(PagePostDetails(responseString));
                    httpBody.close()
                }
               else
                {
                    println("Couldn't fetch content!")
                }
            }
        })
    }
}