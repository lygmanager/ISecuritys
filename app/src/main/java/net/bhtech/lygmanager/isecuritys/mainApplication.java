package net.bhtech.lygmanager.isecuritys;

import android.support.multidex.MultiDexApplication;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.database.DatabaseManager;
import net.bhtech.lygmanager.icons.FontEcModule;
import net.bhtech.lygmanager.net.interceptors.DebugInterceptor;

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
                .withApiHost("http://192.168.0.103:7001/")
                .withInterceptor(new DebugInterceptor("test", R.raw.test))
                .withJavascriptInterface("latte")
                .configure();
            DatabaseManager.getInstance().init(this);

    }
}
