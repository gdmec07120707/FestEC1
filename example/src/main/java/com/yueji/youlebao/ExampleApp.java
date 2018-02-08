package com.yueji.youlebao;

import android.app.Application;

import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latte_core.app.Latte;
import com.example.latte_core.delegates.web.event.TestEvent;
import com.example.latte_core.net.interceptor.DebugInterceptor;
import com.example.latte_core.utils.callback.CallbackManager;
import com.example.latte_core.utils.callback.CallbackType;
import com.example.latte_core.utils.callback.IGlobalCallback;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.yueji.youlebao.R;
import com.yueji.youlebao.event.ShareEvent;

import cn.jpush.android.api.JPushInterface;

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
                .withApiHost("http://117.48.203.104:8089/RestServer/api/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withWeChatAppId("微信id")
                .withWeChatSecret("微信Secret")
                .withJavaScriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .withWebEvent("share",new ShareEvent())
                .configure();
        //initStetho();
        DatabaseManager.getInstance().init(this);

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        CallbackManager.getIntance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
