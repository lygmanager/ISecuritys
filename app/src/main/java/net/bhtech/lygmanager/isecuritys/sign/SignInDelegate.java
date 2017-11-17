package net.bhtech.lygmanager.isecuritys.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.alibaba.fastjson.JSON;

import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.CxfRestClient;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R.id.edit_sign_in_usrId)
    TextInputEditText mUsrId = null;
    @BindView(R.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
           Observable<String> obj=CxfRestClient.builder()
                    .url("login")
                    .params("arg0", mUsrId.getText().toString())
                    .params("arg1", mPassword.getText().toString())
                    .build()
                    .post();
           obj.subscribe(new LatteObserver<String>(_mActivity) {
               @Override
               public void onNext(String o) {
                   UtusrEntity entity=JSON.parseObject(o, UtusrEntity.class);
                   if(entity!=null&&entity.getUSR_ID()!=null&&!"".equals(entity.getUSR_ID())) {
                       SignHandler.onSignIn(entity, mISignListener);
                   }
               }
           });
            //getSupportDelegate().start(new EcBottomDelegate(),SINGLETASK);
        }
    }

    private boolean checkForm() {
        final String usrid = mUsrId.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (usrid.isEmpty() ) {
            mUsrId.setError("用户名不能为空");
            isPass = false;
        } else {
            mUsrId.setError(null);
        }

        if (password.isEmpty() ) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_signin;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
