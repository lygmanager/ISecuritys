package net.bhtech.lygmanager.isecuritys.main.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.sign.ISignListener;
import net.bhtech.lygmanager.isecuritys.sign.SignHandler;
import net.bhtech.lygmanager.isecuritys.sign.SignInDelegate;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class SettingDelegate extends BottomItemDelegate{

    @BindView(R.id.btn_sign_out)
    TextView btn_sign_out=null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.setSignState(false);
                getParentDelegate().getSupportDelegate().start(new SignInDelegate());
            }
        });

    }
}
