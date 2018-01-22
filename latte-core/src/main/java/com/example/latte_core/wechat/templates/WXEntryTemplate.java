package com.example.latte_core.wechat.templates;


import com.example.latte_core.wechat.BaseWXEntryActivity;
import com.example.latte_core.wechat.LatteWeChat;

/**
 * 透明的Activity，使其看起来不用跳转到另一个页面
 */
public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //再次进入的时候结束页面
        finish();
        //不需要切换动画
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
