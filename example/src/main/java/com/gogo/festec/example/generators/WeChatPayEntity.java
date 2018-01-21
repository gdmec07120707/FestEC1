package com.gogo.festec.example.generators;

/**
 * Created by mayn on 2018/1/21.
 */

import com.example.latte_annotations.EntryGenerator;
import com.example.latte_annotations.PayEntryGenelator;
import com.example.latte_core.wechat.templates.AppRegisterTemplate;
import com.example.latte_core.wechat.templates.WXEntryTemplate;

@SuppressWarnings("unused")
@PayEntryGenelator(
        packageName = "com.gogo.festec.example",
        payEntryTemplate = AppRegisterTemplate.class
)
public interface WeChatPayEntity {
}
