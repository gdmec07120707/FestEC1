package com.example.latte_core.delegates.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by mayn on 2018/1/25.
 */

public class WebViewInitializer {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(WebView webView) {
        //设置是否debug模式，需要kikat以上
        //webView.setWebContentsDebuggingEnabled(true);
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "Latte");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件夹权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);

        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }
}
