package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.util.Promise

class RequestFavoritePost(postId : Long, favoriteKey: String) : IRequestAction,
    BaseRequest(BuildConfig.BASE_URL, "fav/$postId/?key=$favoriteKey") {

    override fun performAction() : Promise {
        return GET()
    }
}