package com.example.latte_core.net;

import android.util.Log;

import com.example.latte_core.app.ConfigType;
import com.example.latte_core.app.Latte;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mayn on 2018/1/18.
 * 构建请求实例
 */

public final class RestCreator {

    /**
     * 统一参数容器
     */
    private static final class ParamHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamHolder.PARAMS;
    }

    /**
     * 构建OkHttp
     */
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigType.INTERCEPTOR);

        /**
         * 加入多个拦截器
         *
         * @return
         */
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        /**
         * 创建OkHttpClient
         */
        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

    }

    /**
     * 构建全局Retrofit客户端
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfiguration(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * 构建RestService
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * 获取Service实例
     *
     * @return
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

}
