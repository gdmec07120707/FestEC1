package com.yueji.youlebao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.IError;
import com.example.latte_core.net.callback.IFailure;
import com.example.latte_core.net.callback.ISuccess;
import com.yueji.youlebao.R;

/**
 * Created by mayn on 2018/1/17.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRest();
    }

    public void initRest() {
        RestClient.builder().url("http://127.0.0.1/index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getContext(), "failure", Toast.LENGTH_LONG).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }
}
