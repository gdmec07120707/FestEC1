package com.example.latte_core.utils.demen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.latte_core.app.Latte;

/**
 * Created by mayn on 2018/1/18.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Latte.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
