package com.example.latte_core.app;

/**
 * Created by mayn on 2018/1/17.
 * 枚举类
 * 使用枚举做惰性单例
 */

public enum ConfigType {
    API_HOST,
    APPLICATION_CONTEXT,
    CONFIG_READY,   //控制初始化完成状态
    ICON
}
