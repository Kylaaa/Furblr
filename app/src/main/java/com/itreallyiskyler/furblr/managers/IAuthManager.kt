package com.itreallyiskyler.furblr.managers

import android.webkit.WebView
import com.itreallyiskyler.furblr.util.Signal

interface IAuthManager {
    val UserLoggedIn : Signal<Unit>
    val UserLoggedOut : Signal<Unit>

    fun isAuthenticated() : Boolean

    fun enableCookieSettingsOnWebView(wv : WebView)

    fun syncWebviewCookies(wv : WebView)

    fun logout()
}