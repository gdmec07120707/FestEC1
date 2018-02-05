package com.example.latte_core.utils.callback;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by FANGDINGJIE
 * 2018/2/5.
 */

public interface IGlobalCallback<T>{
    void executeCallback(@NotNull T args);
}
