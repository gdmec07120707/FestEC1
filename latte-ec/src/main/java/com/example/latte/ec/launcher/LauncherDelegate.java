package com.example.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.latte.ec.R;
import com.example.latte.ec.R2;
import com.example.latte_core.app.AccountManager;
import com.example.latte_core.app.IUserChecker;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.ui.launcher.ILauncherListener;
import com.example.latte_core.ui.launcher.OnLauncherFinishTag;
import com.example.latte_core.ui.launcher.ScrollLauncherTag;
import com.example.latte_core.utils.storage.LattePreference;
import com.example.latte_core.utils.timer.BaseTimerTask;
import com.example.latte_core.utils.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mayn on 2018/1/19.
 * 启动页Delegate
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    //这里是在larbary包中构建，需要映入butterknife,否则将无法生产R2文件。使用的时候需要Rebuild一下
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;
    private Timer mTimer = null;
    private int count = 5;
    private ILauncherListener launcherListener = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            launcherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        //第一个参数为延时，第二个参数为多久执行一次
        mTimer.schedule(task, 0, 1000);
    }

    /**
     * 判断是否显示滑动视图
     */
    private void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LUANCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //检查用户是否登陆
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(launcherListener!=null){
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if(launcherListener!=null){
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }

    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", count));
                    count--;
                    if (count < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
