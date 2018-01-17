package com.gogo.festec.example;

import android.app.Application;

import com.example.latte.ec.icon.FontEcModule;
import com.example.latte_core.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by mayn on 2018/1/17.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://www.baidu.com")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .configure();
    }
}
