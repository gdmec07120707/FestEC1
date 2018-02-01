package com.example.latte.ec.pay;

/**
 * Created by mayn on 2018/2/1.
 */

public interface IAlPayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
