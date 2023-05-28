package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.SingletonManager
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

class RequestFavoritePost(postId : Long, favoriteKey: String)
    : BaseRequest(BuildConfig.BASE_URL, "fav/$postId/?key=$favoriteKey") {

    constructor(
        postId: Long,
        favoriteKey: String,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel
    ) : this(postId, favoriteKey) {
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    override fun fetchContent() : Promise {
        return GET()
    }
}