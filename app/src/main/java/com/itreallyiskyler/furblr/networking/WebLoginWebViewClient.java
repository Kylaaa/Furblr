package com.itreallyiskyler.furblr.ui.auth;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebLoginWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        // Catch special cases where navigation should be cancelled

        return false;
    }
}
