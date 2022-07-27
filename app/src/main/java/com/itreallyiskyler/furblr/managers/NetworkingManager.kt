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

object NetworkingManager {
    val CookieHandler : WebviewCookieHandler =
        WebviewCookieHandler()
    val RequestClient : OkHttpClient = OkHttpClient.Builder()
        .cookieJar(CookieHandler)
        .build()
    val requestHandler : RequestHandler = { url, request, resolve, reject ->
        handleRequest(url, request, resolve, reject)
    }
    val logChannel : LoggingChannel = LoggingManager.createChannel("Networking", LogLevel.INFORMATION)

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
                        logChannel.logTrace(httpBody)
                        resolve(body)
                    } else {
                        resolve("")
                    }
                }
            })
        }
    }
}