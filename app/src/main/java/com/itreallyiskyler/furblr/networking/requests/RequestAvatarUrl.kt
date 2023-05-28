package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.util.Promise

class RequestAvatarUrl(username : String, avatarId : Long)
    : BaseRequest(BuildConfig.ASSET_URL, "$avatarId/${username.lowercase()}.gif") {

    override fun fetchContent(): Promise {
       throw Exception("simply use the url, don't fetch the content")
    }
}