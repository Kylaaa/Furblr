package com.itreallyiskyler.furblr.networking.requests

import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.networking.WebviewCookieHandler
import java.net.URL
import java.net.URLEncoder
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.concurrent.thread

val CookieHandler :WebviewCookieHandler = WebviewCookieHandler();
val RequestClient : OkHttpClient = OkHttpClient.Builder()
    .cookieJar(CookieHandler)
    .build()

open class BaseRequest() : IUrlFetcher  {
    // Properties
    private var _baseUrl: String = BuildConfig.BASE_URL;
    private var _path:String = "";
    private var _args: Map<String, Any>? = null;
    private var _url : URL = rebuildUrl();
    private var _logLevel : LogLevel = LogLevel.NONE;

    // Secondary Constructor
    constructor(baseUrl: String,
        path:String = "",
        args: Map<String, Any>? = null) : this() {
        _baseUrl = baseUrl
        _path = path
        _args = args
        _url = rebuildUrl()
    }

    // Accessors and Mutators
    fun getBaseUrl() : String { return _baseUrl; }
    fun getPath() : String { return _path; }
    fun getArgs() : Map<String, Any>? { return _args; }
    override fun getUrl() : URL { return _url; }
    fun setBaseUrl(newBase : String) { _baseUrl = newBase; _url = rebuildUrl(); }
    fun setPath(newPath : String) { _path = newPath; _url = rebuildUrl(); }
    fun setArgs(newArgs : Map<String, Any>? = null) { _args = newArgs; _url = rebuildUrl(); }
    fun setLogLevel(ll : LogLevel) { _logLevel = ll};

    // Functions
    private fun rebuildUrl() : URL {
        return build(_baseUrl, _path, _args)
    }
    private fun build(baseUrl: String, path: String, args: Map<String, Any>? = null) : URL {
        var argString = "";
        if (!args.isNullOrEmpty()){
            var argList = "?";
            for ((k, v) in args){
                argList += URLEncoder.encode(k) + "=" + URLEncoder.encode(v.toString()) + "&";
            }
            argString = argList.substringBeforeLast('&');
        }
        return URL(baseUrl + path + argString);
    }
    private fun fetch(requestBody: String? = null, callback: Callback){
        thread(start=true, name=_url.toString()) {
            var request: Request;
            if (requestBody == null)
                request = Request.Builder()
                    .url(_url)
                    .build()
            else
                request = Request.Builder()
                    .url(_url)
                    .post(requestBody.toRequestBody())
                    .build()

            RequestClient.newCall(request).enqueue(callback);
        }
    }
    protected fun GET(callback: Callback){
        fetch(null, callback)
    }
    protected fun POST(requestBody : String? = null, callback: Callback){
        fetch(requestBody, callback);
    }
}