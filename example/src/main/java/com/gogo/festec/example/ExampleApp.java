package com.gogo.festec.example;

import android.app.Application;

import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latte_core.app.Latte;
import com.example.latte_core.net.interceptor.DebugInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by mayn on 2018/1/17.
 * 京东云服务器
 * http://117.48.203.104:8089/RestServer/data/user_profile.json
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://117.48.203.104:8089/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        initStetho();
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
