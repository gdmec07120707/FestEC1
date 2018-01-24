package com.example.latte_core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * Created by mayn on 2018/1/17.
 *
 */

public class Configurator {
    //WeakHashMap中的键值对，当不在使用的时候会被系统回收，这里的配置是要贯穿整个App的生命周期的，故只能使用HashMap
    private static final HashMap<Object,Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICON = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        //初始化开始
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
        LATTE_CONFIGS.put(ConfigType.HANDLER,HANDLER);
    }

    /**
     * 静态内部类单例模式，线程安全懒汉模式
     */
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    /**
     * 初始化单例
     */
    private  static  class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 配置初始化完成
     */
    public final void configure(){
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    /**
     * 配置主机名称
     * @param host
     * @return
     */
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    private void initIcons(){
        if(ICON.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICON.get(0));
            for(int i=1; i <ICON.size();i++){
                initializer.with(ICON.get(i));
            }
        }
    }

    /**
     * 配置字体库
     * @param descriptor
     * @return
     */
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICON.add(descriptor);
        return this;
    }

    /**
     * 单个拦截器
     * @param interceptor
     * @return
     */
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    /**
     * 多个拦截器
     * @param interceptors
     * @return
     */
    public final Configurator withInterceptor(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    /**
     * 配置微信id
     * @param appId
     * @return
     */
    public final Configurator withWeChatAppId(String appId){
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_ID,appId);
        return this;
    }

    /**
     * 配置微信Secret
     * @param appSecret
     * @return
     */
    public final Configurator withWeChatSecret(String appSecret){
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET,appSecret);
        return this;
    }

    /**
     * 配置当前Activity
     * @param activity
     * @return
     */
    public final Configurator withActivity(Activity activity){
        LATTE_CONFIGS.put(ConfigType.ACTIVITY,activity);
        return this;
    }

    /**
     * 检查配置项
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not ready,call configure()");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if(value == null){
            throw new NullPointerException(key.toString()+" IS NULL");
        }
        return (T)LATTE_CONFIGS.get(key);
    }
}
