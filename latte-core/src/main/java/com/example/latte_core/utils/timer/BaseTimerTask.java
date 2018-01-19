package com.example.latte_core.utils.timer;

import java.util.TimerTask;

/**
 * Created by mayn on 2018/1/19.
 */

public class BaseTimerTask extends TimerTask {
    private ITimerListener timerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.timerListener = timerListener;
    }

    @Override
    public void run() {
        if(timerListener!=null){
            timerListener.onTimer();
        }

    }
}
