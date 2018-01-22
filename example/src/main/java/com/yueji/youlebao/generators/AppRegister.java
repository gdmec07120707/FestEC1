package com.yueji.youlebao.generators;

/**
 * Created by mayn on 2018/1/21.
 */

import com.example.latte_annotations.AppRegisterGenerator;
import com.example.latte_annotations.EntryGenerator;
import com.example.latte_core.wechat.templates.WXEntryTemplate;

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.yueji.youlebao",
        registerTemplate = WXEntryTemplate.class
)
public interface AppRegister {
}
