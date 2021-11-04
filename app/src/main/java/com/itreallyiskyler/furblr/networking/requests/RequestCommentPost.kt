package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.util.Promise

class RequestCommentPost(postId : Long, message : String) : IRequestAction,
    BaseRequest(BuildConfig.BASE_URL, "view/$postId/") {

    val data : HashMap<String, Any> = hashMapOf(
        Pair("f", 0),
        Pair("action", "reply"),
        Pair("replyto", ""),
        Pair("reply", message),
        Pair("submit", "Post Comment")
    )

    override fun performAction(): Promise {
        return POST(data)
    }
}