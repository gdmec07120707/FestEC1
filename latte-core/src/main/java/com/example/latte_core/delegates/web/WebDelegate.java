package com.example.latte_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.latte_core.app.ConfigType;
import com.example.latte_core.app.Latte;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by mayn on 2018/1/25.
 */

public abstract class WebDelegate extends LatteDelegate implements IWebViewInitializer{
    private WebView mWebView = null;
    /*软引用*/
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    /*需要加载的url*/
    private String mUrl = null;
    /*判断Web是否初始化完毕*/
    private boolean mIsWebViewAvailable = false;

    private LatteDelegate mTopDelegate = null;

    public WebDelegate(){

    }

    public abstract IWebViewInitializer initializer();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView(){
        if(mWebView!=null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else{
            final IWebViewInitializer initializer = initializer();
            if(initializer!=null){
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());

                String name = Latte.getConfiguration(ConfigType.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(LatteWebInterface.create(this),name);
                mIsWebViewAvailable = true;
            }else{
                throw  new NullPointerException("Initializer is null!");
            }
        }
    }

    public void setTopDelegate(LatteDelegate delegate){
        mTopDelegate = delegate;
    }

    public LatteDelegate getTopDelegate(){
        if(mTopDelegate ==null){
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    public String getUrl(){
        if(mUrl == null){
            throw new NullPointerException("URL is null");
        }
        return mUrl;
    }

    public WebView getWebView(){
        if(mWebView == null){
            throw new NullPointerException("WebView is null");
        }
        return mIsWebViewAvailable?mWebView:null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWebView!=null){
            mWebView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWebView!=null){
            mWebView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView!=null){
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
