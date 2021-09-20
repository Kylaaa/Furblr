package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.networking.models.PageHome
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RequestHome : IPageParser<PageHome>,
    BaseRequest(BuildConfig.BASE_URL, "") {

    override fun getContent(callback: (PageHome) -> Unit) {
        GET(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val httpBody :String = response.body.toString()
                callback(PageHome(httpBody));
            }
        })

    }
}