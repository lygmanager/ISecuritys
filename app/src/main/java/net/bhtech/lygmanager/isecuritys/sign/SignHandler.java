package net.bhtech.lygmanager.isecuritys.sign;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.AuthUserEntity;
import net.bhtech.lygmanager.database.DatabaseManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;

/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public class SignHandler {
    public static void onSignIn(UtusrEntity entity, ISignListener signListener) {
        if(entity!=null) {
//        DatabaseManager.getInstance().getDao().insert(entity);

            //已经注册并登录成功了
            AccountManager.setSignState(true);
            AccountManager.setSignInfo(entity);
            signListener.onSignInSuccess();
        }else{
            AccountManager.setSignState(false);
            signListener.onSignInFail("密码错误，请重新输入");
        }
    }
}
