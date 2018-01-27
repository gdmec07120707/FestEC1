package com.example.latte_core.delegates.web.event;

import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.latte_core.app.Latte;

/**
 * Created by mayn on 2018/1/25.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params, Toast.LENGTH_LONG).show();
        if(getAction().equals("test")){
            final WebView webView = getWebView();
            Latte.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webView.evaluateJavascript("nativeCall();",null);
                    }
                }
            });
        }
        return null;
    }
}
