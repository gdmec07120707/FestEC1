package com.gogo.festec.example.generators;

/**
 * Created by mayn on 2018/1/21.
 */

import com.example.latte_annotations.AppRegisterGenerator;
import com.example.latte_annotations.EntryGenerator;
import com.example.latte_core.wechat.templates.WXEntryTemplate;

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.gogo.festec.example",
        registerTemplate = WXEntryTemplate.class
)
public interface AppRegister {
}
