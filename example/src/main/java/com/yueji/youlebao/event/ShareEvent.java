package com.yueji.youlebao.event;

import android.widget.Toast;

import com.example.latte_core.app.Latte;
import com.example.latte_core.delegates.web.event.Event;

/**
 * Created by FANGDINGJIE
 * 2018/2/8.
 */

public    class ShareEvent  extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(Latte.getApplicationContext(),"开始分享"+params,Toast.LENGTH_SHORT).show();
        return null;
    }
}
