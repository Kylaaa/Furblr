package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.util.Promise

class RequestLogin : BaseRequest(BuildConfig.BASE_URL, "login") {
    override fun fetchContent(): Promise {
        throw Exception("Cannot request page explicitly. Must use WebView.")
    }
}