package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.SubmissionScrollDirection
import com.itreallyiskyler.furblr.networking.models.SearchOptions
import com.itreallyiskyler.furblr.networking.requests.*
import com.itreallyiskyler.furblr.ui.auth.WebviewCookieHandler
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.LoggingChannel
import okhttp3.*
import java.io.IOException
import java.net.URL
import kotlin.concurrent.thread

class NetworkingManager(
    override val logChannel: LoggingChannel
) : INetworkingManager {
    private val CookieHandler : WebviewCookieHandler =
        WebviewCookieHandler()
    private val RequestClient : OkHttpClient = OkHttpClient.Builder()
        .cookieJar(CookieHandler)
        .build()
    override val requestHandler : RequestHandler = { url, request, resolve, reject ->
        handleRequest(url, request, resolve, reject)
    }

    private fun handleRequest(url : URL, request : Request, resolve : GenericCallback, reject : GenericCallback) {
        thread(start = true, name = url.toString()) {
            RequestClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    logChannel.logError("Failed to fetch $url with error : $e")
                    reject(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    logChannel.logInfo("Resolved $url successfully with code : ${response.code}")
                    val httpBody: ResponseBody? = response.body
                    if (httpBody != null) {
                        val body = httpBody.string()
                        httpBody.close()
                        logChannel.logTrace(body)
                        resolve(body)
                    } else {
                        resolve("")
                    }
                }
            })
        }
    }

    override fun requestAvatarUrl(username : String, avatarId : Long) : IUrlFetcher {
        return RequestAvatarUrl(username, avatarId)
    }

    override fun requestCommentPost(postId : Long, message : String) : IRequestAction {
        return RequestCommentPost(postId, message, requestHandler, logChannel)
    }

    override fun requestFavoritePost(postId : Long, favoriteKey: String) : IRequestAction {
        return RequestFavoritePost(postId, favoriteKey, requestHandler, logChannel)
    }

    override fun requestHome() : IRequestAction {
        return RequestHome(requestHandler, logChannel)
    }

    override fun requestJournalDetails(journalId : Long) : IRequestAction {
        return RequestJournalDetails(journalId, requestHandler, logChannel)
    }

    override fun requestLogin() : IUrlFetcher {
        return RequestLogin()
    }

    override fun requestNotifications() : IRequestAction {
        return RequestNotifications(requestHandler, logChannel)
    }

    override fun requestSearch(keyword : String, searchOptions : SearchOptions) : IRequestAction {
        return RequestSearch(keyword, searchOptions, requestHandler, logChannel)
    }

    override fun requestSubmissions(
        scrollDirection: SubmissionScrollDirection,
        pageSize: Int,
        offsetId: Long?) : IRequestAction {
        return RequestSubmissions(scrollDirection, pageSize, offsetId, requestHandler, logChannel)
    }

    override fun requestUnfavoritePost(postId : Long, favoriteKey: String) : IRequestAction {
        return RequestUnfavoritePost(postId, favoriteKey, requestHandler, logChannel)
    }

    override fun requestUser(userId : String) : IRequestAction {
        return RequestUser(userId, requestHandler, logChannel)
    }

    override fun requestView(postId : Long) : IRequestAction {
        return RequestView(postId, requestHandler, logChannel)
    }

    companion object : IManagerAccessor<NetworkingManager> {
        private lateinit var instance : NetworkingManager
        override fun get(): NetworkingManager {
            return instance
        }
        fun init(
            logChannel: LoggingChannel
        ){
            instance = NetworkingManager(logChannel = logChannel)
        }

    }
}