package com.yueji.youlebao.generators;

/**
 * Created by mayn on 2018/1/21.
 */

import com.example.latte_annotations.EntryGenerator;
import com.example.latte_core.wechat.templates.WXEntryTemplate;

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.yueji.youlebao",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
