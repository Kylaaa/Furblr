package com.itreallyiskyler.furblr.util

import android.webkit.CookieManager
import android.webkit.WebView
import com.itreallyiskyler.furblr.BuildConfig
import java.lang.Exception

object AuthManager {
    val UserLoggedIn : Signal<Unit> = Signal();
    val UserLoggedOut : Signal<Unit> = Signal();

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
            return isAuthenticated
        }
        catch (ex : Exception) {
            return false
        }
    }

    fun enableCookieSettingsOnWebView(wv : WebView) {
        val cm = CookieManager.getInstance();
        cm.setAcceptThirdPartyCookies(wv, true);
        cm.setAcceptCookie(true);
    }

    fun syncWebviewCookies(_ : WebView) {
        CookieManager.getInstance().flush();
    }

    fun logout() {
        CookieManager.getInstance().removeAllCookies() { value ->
            print("Logging out and removing cookies : $value");
        };
    }
}
