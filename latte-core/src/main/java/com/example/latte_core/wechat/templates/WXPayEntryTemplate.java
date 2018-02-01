package com.example.latte_core.wechat.templates;

import android.widget.Toast;

import com.example.latte_core.activities.ProxyActivity;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.wechat.BaseWXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;


/**
 * 生产代码模板类
 */

public class WXPayEntryTemplate extends BaseWXPayEntryActivity {


    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
