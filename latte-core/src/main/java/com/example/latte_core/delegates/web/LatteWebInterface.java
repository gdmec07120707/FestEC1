package com.example.latte_core.delegates.web;

import android.webkit.JavascriptInterface;

/**
 * Created by mayn on 2018/1/25.
 */

public class LatteWebInterface {

    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    @JavascriptInterface
    public String event(String params){
        return null;
    }
}
