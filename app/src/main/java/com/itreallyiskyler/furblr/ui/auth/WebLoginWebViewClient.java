package com.itreallyiskyler.furblr.ui.auth;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itreallyiskyler.furblr.networking.requests.RequestHome;
import com.itreallyiskyler.furblr.networking.requests.RequestLogin;
import com.itreallyiskyler.furblr.util.AuthManager;
import com.itreallyiskyler.furblr.util.Command;
import com.itreallyiskyler.furblr.util.CommandWithArgs2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
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
           put(LOGIN_PATH, (view, url) -> removeExtraLoginPageElements(view));
        }};

        _removableClasses = new String[]{
            "mobile-navigation",
            "leaderboardAd"
        };

        _removableIds = new String[]{
            "ddmenu",
            "header",
            "footer",
            "cookie-notification"
        };
    };

    private String getRemoveElementJS() {
        // define the JS function
        ArrayList<String> js = new ArrayList<String>();
        js.add("var removeElement = function(name, isId) {");
            js.add("try {");
                js.add("if (isId) {");
                    js.add("var item = document.getElementById(name);");
                    js.add("item.remove();");
                js.add("} else {");
                    js.add("var items = document.getElementsByClassName(name);");
                    js.add("for (var i = 0; i < items.length; i++) {");
                        js.add("items[i].remove();");
                    js.add("}");
                js.add("}");
            js.add("} catch(e){");
                js.add("console.log(e);");
            js.add("};");
        js.add("};");
        js.add("var removeTargetElements = function() {");


        // remove all of the known elements from the page
        for (String className : _removableClasses) {
            js.add(String.format("removeElement(\"%s\", false);", className));
        }
        for (String idName : _removableIds) {
            js.add(String.format("removeElement(\"%s\", true);", idName));
        }

        // close the custom handler
        js.add("};");

        // add event listeners to try to handle when the page loads
        js.add("document.addEventListener(\"DOMContentLoaded\", removeTargetElements);");
        js.add("window.onload = removeTargetElements;");
        js.add("removeTargetElements();");

        // convert it to a string for injection into the page
        String jsBlob = TextUtils.join(" ", js);
        return jsBlob;
    }

    private Boolean removeExtraLoginPageElements(WebView view)
    {
        // remove elements from the view so the user can't navigate places
        String js = getRemoveElementJS();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            view.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.println(Log.INFO, "JS", s);
                }
            });
        } else {
            String legacyJs = "javascript:" + js;
            view.loadUrl(legacyJs);
        }

        // no-opt
        return false;
    }

    private Boolean setAsLoggedIn()
    {
        // TODO : check if session is _actually_ authenticated
        //AuthManager.INSTANCE.isAuthenticated();
        AuthManager.INSTANCE.getUserLoggedIn().fire(null);
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
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        /*String path = getPath(url);
        if (path == LOGIN_PATH)
        {
            CommandWithArgs2<Boolean, WebView, String> cmd = _routes.get(LOGIN_PATH);
            cmd.invoke(view, url);
        }*/
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
    {
        String url = request.getUrl().toString();
        Log.println(Log.INFO, "WV", url.toString());
        String path = getPath(url);

        Log.println(Log.INFO, "WV", path);
        //if (_routes.containsKey(path))
        if (path.compareTo(HOME_PATH) == 0)
        {
            CommandWithArgs2<Boolean, WebView, String> cmd = _routes.get(HOME_PATH);
            return cmd.invoke(view, url);
        }

        return super.shouldOverrideUrlLoading(view, request);
    }

    public void initCookieSettings(WebView view)
    {
        AuthManager.INSTANCE.enableCookieSettingsOnWebView(view);
    }
}
