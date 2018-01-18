package com.example.latte_core.app.net.download;

import android.os.AsyncTask;

import com.example.latte_core.app.net.RestCreator;
import com.example.latte_core.app.net.callback.IError;
import com.example.latte_core.app.net.callback.IFailure;
import com.example.latte_core.app.net.callback.IRequest;
import com.example.latte_core.app.net.callback.ISuccess;
import java.util.WeakHashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by mayn on 2018/1/18.
 */

public class DownloadHandler {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public DownloadHandler(String url, IRequest request, String downloadDir, String extention, String name, ISuccess success, IError error, IFailure failure) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extention;
        this.NAME = name;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
    }

    public final void handleDownload(){
        if(REQUEST!=null){
            REQUEST.onRequestStart();
        }

        RestCreator
                .getRestService()
                .download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask mTask = new SaveFileTask(REQUEST,SUCCESS);
                            mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR,EXTENSION,NAME);

                            if(mTask.isCancelled()){
                                if(REQUEST!=null){
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else{
                            if(ERROR!=null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }

                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE!=null){
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });

    }
}
