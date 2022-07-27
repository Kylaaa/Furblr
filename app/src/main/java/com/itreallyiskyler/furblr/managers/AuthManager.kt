package com.itreallyiskyler.furblr.managers

import android.webkit.CookieManager
import android.webkit.WebView
import com.itreallyiskyler.furblr.BuildConfig
import com.itreallyiskyler.furblr.enum.LogLevel
import com.itreallyiskyler.furblr.util.Signal
import java.lang.Exception

object AuthManager {
    val UserLoggedIn : Signal<Unit> = Signal()
    val UserLoggedOut : Signal<Unit> = Signal()
    val loggingChannel = LoggingManager.createChannel("Authentication", LogLevel.ERROR)

    fun isAuthenticated() : Boolean {
        try {
            val sessionCookies = CookieManager.getInstance().getCookie(BuildConfig.BASE_URL)
            val cookiesArr = sessionCookies.split(";")

            val cookieDict: MutableMap<String, String> = mutableMapOf()
            cookiesArr.forEach { c: String ->
                run {
                    val parts = c.split("=")
                    cookieDict.set(parts[0].trim(), parts[1])
                }
            }

            // Thank you https://github.com/Deer-Spangle/faexport for describing how the
            // authentication system works
            val isAuthenticated = cookieDict.containsKey("a") and cookieDict.containsKey("b")
            loggingChannel.logTrace("Is Authenticated? $isAuthenticated")
            return isAuthenticated
        }
        catch (ex : Exception) {
            loggingChannel.logError("Failed to parse the session cookie : $ex")
            return false
        }
    }

    fun enableCookieSettingsOnWebView(wv : WebView) {
        val cm = CookieManager.getInstance();
        cm.setAcceptThirdPartyCookies(wv, true);
        cm.setAcceptCookie(true);
    }

    fun syncWebviewCookies(wv : WebView) {
        CookieManager.getInstance().flush();
    }

    fun logout() {
        CookieManager.getInstance().removeAllCookies() { value ->
            loggingChannel.logTrace("Logging out and removing cookies : $value");
        };
    }
}