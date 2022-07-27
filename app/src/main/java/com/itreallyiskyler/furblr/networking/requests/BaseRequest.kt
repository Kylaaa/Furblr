package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.managers.NetworkingManager
import com.itreallyiskyler.furblr.util.GenericCallback
import com.itreallyiskyler.furblr.util.LoggingChannel
import com.itreallyiskyler.furblr.util.Promise
import java.net.URL
import java.net.URLEncoder
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.EMPTY_REQUEST
import kotlin.collections.HashMap

private enum class RequestType {
    GET,
    POST,
    PUT,
    DELETE
}
typealias RequestHandler = (URL, Request, GenericCallback, GenericCallback)->Unit

open abstract class BaseRequest() : IUrlFetcher, IRequestAction  {
    // Properties
    private var _baseUrl: String = BuildConfig.BASE_URL
    private var _path:String = ""
    private var _args: Map<String, Any>? = null
    private var _url : URL = rebuildUrl()
    private var _requestImpl : RequestHandler = NetworkingManager.requestHandler
    private var _loggingChannel : LoggingChannel = NetworkingManager.logChannel

    // Additional Constructors
    constructor(
        baseUrl: String,
        path:String = "",
        args: Map<String, Any>? = null,
        requestHandler : RequestHandler = NetworkingManager.requestHandler,
        loggingChannel: LoggingChannel = NetworkingManager.logChannel) : this() {
        _baseUrl = baseUrl
        _path = path
        _args = args
        _url = rebuildUrl()
        _requestImpl = requestHandler
        _loggingChannel = loggingChannel
    }

    // Accessors and Mutators
    fun getBaseUrl() : String { return _baseUrl }
    fun getPath() : String { return _path }
    fun getArgs() : Map<String, Any>? { return _args }
    protected fun getLogChannel() : LoggingChannel { return _loggingChannel }
    override fun getUrl() : URL { return _url }
    fun setBaseUrl(newBase : String) { _baseUrl = newBase; _url = rebuildUrl() }
    fun setPath(newPath : String) { _path = newPath; _url = rebuildUrl() }
    fun setArgs(newArgs : Map<String, Any>? = null) { _args = newArgs; _url = rebuildUrl() }
    fun setRequestHandler(newHandler : RequestHandler) { _requestImpl = newHandler }
    fun setLoggingChannel(newChannel: LoggingChannel) { _loggingChannel = newChannel }

    // Functions
    private fun rebuildUrl() : URL {
        return build(_baseUrl, _path, _args)
    }
    private fun build(baseUrl: String, path: String, args: Map<String, Any>? = null) : URL {
        var argString = ""
        if (!args.isNullOrEmpty()){
            var argList = "?"
            for ((k, v) in args){
                argList += URLEncoder.encode(k) + "=" + URLEncoder.encode(v.toString()) + "&"
            }
            argString = argList.substringBeforeLast('&')
        }
        return URL(baseUrl + path + argString)
    }
    private fun fetch(requestType : RequestType, requestBody: String? = null) : Promise {
        // TODO : figure out PUT and DELETE support
        val action = fun(resolve : GenericCallback, reject : GenericCallback) {
            var request : Request.Builder = Request.Builder()
            request.url(_url)

            val body : RequestBody = if (requestBody != null) requestBody.toRequestBody() else EMPTY_REQUEST

            when (requestType) {
                RequestType.GET -> request = request.get()
                RequestType.POST -> request = request.post(body)
                RequestType.PUT -> request = request.put(body)
                RequestType.DELETE -> request = request.delete(body)
            }
            _requestImpl(_url, request.build(), resolve, reject)
        }
        return Promise(action)
    }
    private fun encodeFormData(formData: HashMap<String, Any>?) : String {
        var requestBody = ""
        if (formData != null) {
            for ((key : String, value : Any) in formData) {
                requestBody += "$key=${value};"
            }
            // remove the trailing semi-colon
            requestBody = URLEncoder.encode(requestBody.substring(0, requestBody.length - 1))
        }
        return requestBody
    }

    // explicit request types
    protected fun GET() : Promise {
        return fetch(RequestType.GET, null)
    }
    protected fun POST(requestBody : String? = null) : Promise {
        return fetch(RequestType.POST, requestBody)
    }
    protected fun POST(formData : HashMap<String, Any>? = null) : Promise {
        var requestBody = encodeFormData(formData)
        return fetch(RequestType.POST, requestBody)
    }
}