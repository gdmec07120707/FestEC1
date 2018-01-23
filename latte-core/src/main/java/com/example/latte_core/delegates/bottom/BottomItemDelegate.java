package com.example.latte_core.delegates.bottom;

import android.widget.Toast;

import com.example.latte_core.delegates.LatteDelegate;

/**
 * Created by mayn on 2018/1/23.
 */

public abstract class BottomItemDelegate  extends LatteDelegate{
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if(System.currentTimeMillis()-TOUCH_TIME<WAIT_TIME){
            _mActivity.finish();
        }else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
