package com.example.latte_core.delegates.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.latte_core.delegates.web.WebDelegate;
import com.example.latte_core.delegates.web.route.Router;
import com.example.latte_core.utils.log.LatteLogger;


/**
 * Created by mayn on 2018/1/25.
 */

public class WebViewClientImpl extends WebViewClient{
    private final WebDelegate DELEGATE;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading",url);
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }
}
