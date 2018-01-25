package com.example.latte_core.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by mayn on 2018/1/25.
 */

public class WebChromeClientImpl extends WebChromeClient{

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
