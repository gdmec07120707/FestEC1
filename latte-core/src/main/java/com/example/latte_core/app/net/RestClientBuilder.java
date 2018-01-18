package com.example.latte_core.app.net;

import android.content.Context;

import com.example.latte_core.app.net.callback.IError;
import com.example.latte_core.app.net.callback.IFailure;
import com.example.latte_core.app.net.callback.IRequest;
import com.example.latte_core.app.net.callback.ISuccess;
import com.example.latte_core.app.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by mayn on 2018/1/18.
 * 使用建造者模式，类似于AlertDialog的设计模式
 */

public class RestClientBuilder {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IError mIError = null;
    private IFailure mIFailure = null;
    private RequestBody mBody = null;
    private File mFile = null;
    private LoaderStyle loaderStyle = null;
    private Context context = null;

    private String downloadDir= null;
    private String extention= null;
    private String name= null;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> param) {
        PARAMS.putAll(param);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name){
        this.name = name;
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.downloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extention){
        this.extention = extention;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest mIRequest) {
        this.mIRequest = mIRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.loaderStyle = loaderStyle;
        this.context = context;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.loaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        this.context = context;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,downloadDir,extention,name, mIRequest, mISuccess, mIError, mIFailure, mBody, mFile, context, loaderStyle);
    }
}
