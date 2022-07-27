package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise

class RequestCommentPost(postId : Long, message : String)
    : BaseRequest(BuildConfig.BASE_URL, "view/$postId/") {

    constructor(
        postId : Long,
        message : String,
        requestHandler: RequestHandler,
        loggingChannel: LoggingChannel = NetworkingManager.logChannel
    ) :  this(postId, message){
        setRequestHandler(requestHandler)
        setLoggingChannel(loggingChannel)
    }

    val data : HashMap<String, Any> = hashMapOf(
        Pair("f", 0),
        Pair("action", "reply"),
        Pair("replyto", ""),
        Pair("reply", message),
        Pair("submit", "Post Comment")
    )

    override fun fetchContent(): Promise {
        return POST(data)
    }
}