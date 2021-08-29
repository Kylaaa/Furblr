package com.itreallyiskyler.furblr.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import com.itreallyiskyler.furblr.R
import com.itreallyiskyler.furblr.networking.WebLoginWebViewClient
import com.itreallyiskyler.furblr.networking.requests.RequestLogin
import com.itreallyiskyler.furblr.util.AuthManager

class WebLoginFragment : Fragment() {

    private lateinit var _wv : WebView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        // grab the webview from the view
        _wv = view?.findViewById(R.id.webview)!!

        // enable JavaScript to allow ReCaptcha to work
        _wv.settings.javaScriptEnabled = true

        // attach a client to handle special navigation logic
        var wvClient = WebLoginWebViewClient();
        wvClient.initCookieSettings(_wv);
        _wv.webViewClient = wvClient;

        // load the login page
        var targetUrl = RequestLogin().getUrl().toString()
        _wv.loadUrl(targetUrl)
    }

    companion object {
        /**
         *
         * @return A new instance of fragment WebLoginFragment.
         */
        @JvmStatic
        fun newInstance() =
            WebLoginFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}