package com.example.latte_core.net;

import android.content.Context;
import com.example.latte_core.net.callback.IError;
import com.example.latte_core.net.callback.IFailure;
import com.example.latte_core.net.callback.IRequest;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.net.callback.RequestCallback;
import com.example.latte_core.net.download.DownloadHandler;
import com.example.latte_core.ui.loader.LatteLoader;
import com.example.latte_core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
/**
 * Created by mayn on 2018/1/17.
 */

public class RestClient {
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;

    public RestClient(String url,
                      Map<String, Object> params,
                      String downloadDir,
                      String extention,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      File mFile,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extention;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = mFile;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMehod mothod) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if(LOADER_STYLE!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (mothod) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = service.upload(URL,body);
                break;
            default:
                break;
        }

        if (call != null) {
            //enqueue在后台线程中执行
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallback(REQUEST, SUCCESS, ERROR, FAILURE,LOADER_STYLE);
    }

    public final void get() {
        request(HttpMehod.GET);
    }

    public final void post() {
        if(BODY == null){
            request(HttpMehod.POST);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must bu null");
            }
            request(HttpMehod.POST_RAW);
        }

    }

    public final void put() {
        if(BODY == null){
            request(HttpMehod.PUT);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must bu null");
            }
            request(HttpMehod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMehod.DELETE);
    }

    public final void upload() {
        request(HttpMehod.UPLOAD);
    }

    public final void download() {
       new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,ERROR,FAILURE).handleDownload();
    }
}
