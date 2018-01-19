package com.example.latte_core.net.callback;

/**
 * Created by mayn on 2018/1/18.
 */

public interface IRequest {
    /**
     * 开始请求
     */
    void onRequestStart();

    /**
     * 请求结束
     */
    void onRequestEnd();

}
