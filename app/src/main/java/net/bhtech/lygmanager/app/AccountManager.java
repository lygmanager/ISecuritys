package net.bhtech.lygmanager.app;

import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;


/**
 * Created by zhangxinbiao on 2017/11/12.
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG,
        SIGN_USER
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    //保存用户登录状态，登录后调用
    public static void setSignInfo(UtusrEntity user) {
        String userStr=JSONObject.toJSONString(user);
        LattePreference.addCustomAppProfile("SIGN_USER", userStr);
    }

    public static UtusrEntity getSignInfo() {
        String userStr=LattePreference.getCustomAppProfile("SIGN_USER");
        UtusrEntity user=JSONObject.parseObject(userStr,UtusrEntity.class);
        return user;
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
