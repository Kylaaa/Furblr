package com.itreallyiskyler.furblr.managers

import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.networking.requests.RequestHandler
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