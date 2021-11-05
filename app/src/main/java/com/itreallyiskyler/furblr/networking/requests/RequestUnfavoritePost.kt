package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.util.Promise

class RequestUnfavoritePost(postId : Long, favoriteKey: String) : IRequestAction,
    BaseRequest(BuildConfig.BASE_URL, "unfav/$postId/?key=$favoriteKey") {

    override fun performAction() : Promise {
        return GET()
    }
}