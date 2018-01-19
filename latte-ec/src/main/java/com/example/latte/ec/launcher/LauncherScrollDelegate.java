package com.example.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.latte.ec.R;
import com.example.latte_core.app.AccountManager;
import com.example.latte_core.app.IUserChecker;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_core.ui.launcher.ILauncherListener;
import com.example.latte_core.ui.launcher.LauncherHolderCreator;
import com.example.latte_core.ui.launcher.OnLauncherFinishTag;
import com.example.latte_core.ui.launcher.ScrollLauncherTag;
import com.example.latte_core.utils.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by mayn on 2018/1/19.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> convenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener launcherListener = null;

    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        convenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(true);
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
        convenientBanner = new ConvenientBanner<Integer>(getContext());
        return convenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }


    @Override
    public void onItemClick(int position) {
        //点击了最后一个
        if(position == INTEGERS.size()-1){
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LUANCHER_APP.name(),true);

            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (launcherListener != null) {
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (launcherListener != null) {
                        launcherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }

    }
}
