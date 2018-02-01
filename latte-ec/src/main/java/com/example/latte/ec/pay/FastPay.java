package com.example.latte.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ec.R;
import com.example.latte_core.app.ConfigType;
import com.example.latte_core.app.Latte;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.net.RestClient;
import com.example.latte_core.net.callback.ISuccess;
import com.example.latte_core.ui.loader.LatteLoader;
import com.example.latte_core.utils.log.LatteLogger;
import com.example.latte_core.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by FANGDINGJIE
 * 2018/2/1.
 */
public class FastPay implements View.OnClickListener{
    //设置支付回调监听
    private IAlPayResultListener mIAlPayResultListener = null;

    private AlertDialog mDialog = null;
    private Activity mActivity = null;
    private int mOrderID = -1;

    private FastPay(LatteDelegate delegate){
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(LatteDelegate delegate){
        return new FastPay(delegate);
    }

    public void beginPayDialog(){
        mDialog.show();
        final Window window = mDialog.getWindow();
        if(window!=null){
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);

        }
    }

    /**
     * 设置订单id
     * @param orderID
     * @return
     */
    public FastPay setOrderID(int orderID){
        this.mOrderID = orderID;
        return this;
    }

    /**
     * 设置回调监听
     * @param listener
     * @return
     */
    public FastPay setPayResultListener(IAlPayResultListener listener) {
        this.mIAlPayResultListener = listener;
        return this;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_dialog_pay_alpay) {
            mDialog.cancel();
            alPay(mOrderID);
        } else if (id == R.id.btn_dialog_pay_wechat) {
            mDialog.cancel();
            weChatPay(mOrderID);
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }
    }

    /**
     * 微信支付
     * @param orderId
     */
    private void weChatPay(int orderId){
        LatteLoader.stopLoading();
        //获取微信请求参数的地址
        final String weChatPrePayUrl = "你的服务端微信预支付地址" + orderId;

        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String appId = Latte.getConfiguration(ConfigType.WE_CHAT_APP_ID);
        iwxapi.registerApp(appId);

        RestClient.builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //获取调起微信请求需要的字段
                        final JSONObject result =
                                JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    /**
     * 支付宝支付
     * @param orderId
     */
    private void alPay(int orderId){
        final String singUrl = "你的服务端支付地址" + orderId;
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN", paySign);
                        //必须是异步的调用客户端支付接口
                        ALPayAsyncTask mAlPayAsync = new ALPayAsyncTask(mActivity,mIAlPayResultListener);
                        mAlPayAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,paySign);
                    }
                })
                .build()
                .post();
    }
}
