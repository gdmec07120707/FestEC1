package com.example.latte_core.app;

import com.example.latte_core.utils.storage.LattePreference;

/**
 * Created by mayn on 2018/1/19.
 */

public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }

    /**
     * 保存用户状态
     * @param state
     */
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    /**
     * 判断是否登陆过
     * @return
     */
    public static boolean isSign(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }


    /**
     * 检查用户状态并回调
     * @param checker
     */
    public static void checkAccount(IUserChecker checker){
        if(isSign()){
            checker.onSignIn();
        }else{
            checker.onNotSignIn();
        }
    }
}
