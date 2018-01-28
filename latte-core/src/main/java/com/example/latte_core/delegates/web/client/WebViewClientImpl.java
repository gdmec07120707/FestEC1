package com.example.latte_core.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.latte_core.app.Latte;
import com.example.latte_core.delegates.IPageLoadListener;
import com.example.latte_core.delegates.web.WebDelegate;
import com.example.latte_core.delegates.web.route.Router;
import com.example.latte_core.ui.loader.LatteLoader;
import com.example.latte_core.utils.log.LatteLogger;


/**
 * Created by mayn on 2018/1/25.
 */

public class WebViewClientImpl extends WebViewClient{
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public void setPageLoadListener(IPageLoadListener mIPageLoadListener){
        this.mIPageLoadListener = mIPageLoadListener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading",url);
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadEnd();
        }
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        },1000);
    }
}
