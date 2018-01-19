package com.example.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ec.database.DatabaseManager;
import com.example.latte.ec.database.UserProfile;
import com.example.latte_core.app.AccountManager;

/**
 * Created by mayn on 2018/1/19.
 */

public class SignHandler {
    /**
     * 登陆
     * @param response
     * @param mISignListener
     */
    public static void onSignIn(String response,ISignListener mISignListener){//,ISignListener signListener
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmDao().insert(profile);

        AccountManager.setSignState(true);
        mISignListener.onSignInSuccess();
    }


    /**
     * 注册
     * @param response
     * @param mISignListener
     */
    public static void onSignUp(String response,ISignListener mISignListener){//,ISignListener signListener
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getmDao().insert(profile);

        AccountManager.setSignState(true);
        mISignListener.onSignUpSuccess();
    }
}
