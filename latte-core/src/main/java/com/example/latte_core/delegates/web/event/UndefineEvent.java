package com.example.latte_core.delegates.web.event;

import com.example.latte_core.utils.log.LatteLogger;

/**
 * Created by mayn on 2018/1/25.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.d("UndefineEvent",params);
        return null;
    }
}
