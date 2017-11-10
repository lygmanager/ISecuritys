package net.bhtech.lygmanager.isecuritys;

import android.support.multidex.MultiDexApplication;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.icons.FontEcModule;

/**
 * Created by zhangxinbiao on 2017/11/10.
 */

public class mainApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost("你的本地服务器地址")
//                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("latte")
                .configure();
//            DatabaseManager.getInstance().init(this);

    }
}
