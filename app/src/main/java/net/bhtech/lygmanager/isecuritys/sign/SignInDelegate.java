package net.bhtech.lygmanager.isecuritys.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class SignInDelegate extends LatteDelegate {

    @BindView(R.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
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
//            RestClient.builder()
//                    .url("http://192.168.56.1:8080/RestDataServer/api/user_profile.php")
//                    .params("email", mEmail.getText().toString())
//                    .params("password", mPassword.getText().toString())
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//                            LatteLogger.json("USER_PROFILE", response);
//                            SignHandler.onSignIn(response, mISignListener);
//                        }
//                    })
//                    .build()
//                    .post();
        }
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

//        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            mEmail.setError("错误的邮箱格式");
//            isPass = false;
//        } else {
//            mEmail.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 6) {
//            mPassword.setError("请填写至少6位数密码");
//            isPass = false;
//        } else {
//            mPassword.setError(null);
//        }

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
