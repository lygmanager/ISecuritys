package net.bhtech.lygmanager.isecuritys;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import net.bhtech.lygmanager.activities.ProxyActivity;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.launcher.ILauncherListener;
import net.bhtech.lygmanager.isecuritys.launcher.LauncherDelegate;
import net.bhtech.lygmanager.isecuritys.launcher.OnLauncherFinishTag;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.sign.ISignListener;
import net.bhtech.lygmanager.isecuritys.sign.SignInDelegate;

public class SplashActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
//        StatusBarCompat.translucentStatusBar(this, true);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignInFail(String meg) {
        Toast.makeText(this, meg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
