package com.itreallyiskyler.testhelpers.managers

import android.webkit.WebView
import com.itreallyiskyler.furblr.managers.IAuthManager
import com.itreallyiskyler.furblr.util.Signal

open class MockAuthManager : IAuthManager {
    override val UserLoggedIn = Signal<Unit>()
    override val UserLoggedOut = Signal<Unit>()
    override fun enableCookieSettingsOnWebView(wv: WebView) {}
    override fun isAuthenticated(): Boolean {
        return true
    }
    override fun logout() {}
    override fun syncWebviewCookies(wv: WebView) {}
}