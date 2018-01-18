package com.example.latte_core.app.net.callback;
import android.os.Handler;
import com.example.latte_core.app.ui.loader.LatteLoader;
import com.example.latte_core.app.ui.loader.LoaderStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayn on 2018/1/18.
 */

public class RequestCallback implements Callback {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderStyle LOADER_STYLE;
    private static final  Handler HANDLER = new Handler();

    public RequestCallback(IRequest request, ISuccess success, IError error, IFailure failure,LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS!=null){
                    SUCCESS.onSuccess(response.body().toString());
                }
            }
        }else {
            if(ERROR!=null){
                ERROR.onError(response.code(),response.body().toString());
            }
        }
        onRequestFinish();


    }

    @Override
    public void onFailure(Call call, Throwable t) {
        if(FAILURE!=null){
            FAILURE.onFailure();
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        onRequestFinish();

    }

    /**
     * 请求结束，关闭对话框
     */
    private void onRequestFinish(){
        //final long
        if(LOADER_STYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }
}
