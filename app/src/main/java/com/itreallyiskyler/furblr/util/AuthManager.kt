package com.itreallyiskyler.furblr.util
import android.app.Activity
import android.webkit.CookieManager;
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.fragment.app.FragmentManager
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.ui.auth.WebLoginFragment
import okhttp3.Cookie

object AuthManager {

    private lateinit var _mainFragmentManager : FragmentManager;
    private lateinit var _currentLoginFragment : WebLoginFragment;

    val UserLoggedIn : Signal<Unit> = Signal();
    val UserLoggedOut : Signal<Unit> = Signal();


    fun isAuthenticated() : Boolean {
        return CookieManager.getInstance().hasCookies();

        /*var COOKIE_NAME : String = ""
        var sessionCookie = CookieManager.getInstance().getCookie(COOKIE_NAME)
        return sessionCookie.isNotEmpty()*/
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
            print("Logging out and removing cookies : $value");
        };
    }

    fun showLogin(fm : FragmentManager, rootLayoutId : Int)
    {
        _currentLoginFragment = WebLoginFragment()

        val tr = fm.beginTransaction()
        tr.add(rootLayoutId, _currentLoginFragment)
        tr.commitAllowingStateLoss()

        _mainFragmentManager = fm
    }

    fun hideLogin(){
        val tr = _mainFragmentManager.beginTransaction()
        tr.remove(_currentLoginFragment)
        tr.commitAllowingStateLoss()

        // clean up
        //_mainFragmentManager = null
        //_currentLoginFragment = null

        // DEBUG
        ContentManager.fetchSubmissions();
    }

}