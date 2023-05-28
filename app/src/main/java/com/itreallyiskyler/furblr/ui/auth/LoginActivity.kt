package com.itreallyiskyler.furblr.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import com.itreallyiskyler.furblr.databinding.ActivityLoginBinding
import com.itreallyiskyler.furblr.networking.requests.RequestLogin
import com.itreallyiskyler.furblr.ui.main.MainActivity
import com.itreallyiskyler.furblr.managers.AuthManager
import com.itreallyiskyler.furblr.managers.SingletonManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var connections : ArrayList<()->Unit> = arrayListOf();

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the webview
        val _wv : WebView = binding.wvLogin

        // enable JavaScript to allow ReCaptcha to work
        _wv.settings.javaScriptEnabled = true

        // attach a client to handle special navigation logic
        var wvClient = WebLoginWebViewClient();
        wvClient.initCookieSettings(_wv);
        _wv.webViewClient = wvClient;

        // load the login page
        var targetUrl = RequestLogin().getUrl().toString()
        _wv.loadUrl(targetUrl)

        // listen for the login signal to fire
        val loginCnx = SingletonManager.get().AuthManager.UserLoggedIn.connect { onUserLogin() };
        connections.add(loginCnx)
    }

    private fun onUserLogin() {
        val gotoHomeIntent : Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("test", "test")
        }
        startActivity(gotoHomeIntent)
    }

    override fun onDestroy() {
        // disconnect all the connections
        for (cnx : ()->Unit in connections) {
            cnx()
        }

        super.onDestroy()
    }
}