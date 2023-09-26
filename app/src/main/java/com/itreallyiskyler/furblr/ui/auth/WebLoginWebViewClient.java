package com.itreallyiskyler.furblr.ui.auth;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itreallyiskyler.furblr.managers.SingletonManager;
import com.itreallyiskyler.furblr.networking.requests.RequestHome;
import com.itreallyiskyler.furblr.networking.requests.RequestLogin;
import com.itreallyiskyler.furblr.util.CommandWithArgs2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import kotlin.Unit;


public class WebLoginWebViewClient extends WebViewClient {
    String HOME_PATH = new RequestHome().getUrl().getPath();
    String LOGIN_PATH = new RequestLogin().getUrl().getPath();
    private HashMap<String, CommandWithArgs2<Boolean, WebView, String>> _routes;
    private String[] _removableClasses;
    private String[] _removableIds;

    public WebLoginWebViewClient(){
        _routes = new HashMap<String, CommandWithArgs2<Boolean, WebView, String>>() {{
           put(HOME_PATH, (view, url) -> setAsLoggedIn());
           put(LOGIN_PATH, (view, url) -> checkAuthentication());
        }};
    };

    private Boolean checkAuthentication(){
        if (SingletonManager.Companion.get().getAuthManager().isAuthenticated()) {
            return setAsLoggedIn();
        }

        return true;
    }

    private Boolean setAsLoggedIn()
    {
        // TODO : check if session is _actually_ authenticated
        SingletonManager.Companion.get().getAuthManager().getUserLoggedIn().fire(Unit.INSTANCE);
        return true;
    }

    private String getPath(String url) {
        String path = url;
        try {
            path = new URL(url).getPath();
        }
        catch (MalformedURLException e)
        {
            path = url;
        }
        return path;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
    {
        String url = request.getUrl().toString();
        Log.println(Log.INFO, "WV", url.toString());
        String path = getPath(url);

        Log.println(Log.INFO, "WV", path);
        if (path.compareTo(HOME_PATH) == 0)
        {
            CommandWithArgs2<Boolean, WebView, String> cmd = _routes.get(HOME_PATH);
            return cmd.invoke(view, url);
        }

        return super.shouldOverrideUrlLoading(view, request);
    }

    public void initCookieSettings(WebView view)
    {
        SingletonManager.Companion.get().getAuthManager().enableCookieSettingsOnWebView(view);
    }
}
