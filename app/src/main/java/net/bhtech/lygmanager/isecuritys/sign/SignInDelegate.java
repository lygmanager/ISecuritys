package net.bhtech.lygmanager.isecuritys.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.database.AuthUserEntity;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.net.RestClient;
import net.bhtech.lygmanager.net.cxfweservice.CxfRestClient;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.JsonResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

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
//           Observable<String> obj=
//                   RxRestClient.builder()
//                    .url("http://123.56.229.253:8080/Tube42/login.do?func=login")
//                    .params("userId", mUsrId.getText().toString())
//                    .params("userPwd", mPassword.getText().toString())
//                    .build()
//                    .post();
//           obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
//               @Override
//               public void onNext(String o) {
//                   JsonResult jsonResult=JSON.parseObject(o, JsonResult.class);
//                   if(jsonResult!=null&&jsonResult.isSuccess()) {
//                       AuthUserEntity entity=JSONObject.toJavaObject((JSONObject)jsonResult.getObj(),AuthUserEntity.class);
//                       LatteLogger.d(entity.getUserNam());
//                       SignHandler.onSignIn(entity, mISignListener);
//                   }
//               }
//           });
            Observable<String> obj1=
                    RxRestClient.builder()
                            .url("loginApp")
                            .params("userName", mUsrId.getText().toString())
                            .params("password", mPassword.getText().toString())
                            .build()
                            .post();
            obj1.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String o) {
                    LatteLogger.d(o);
                    UtusrEntity entity = JSON.parseObject(o, UtusrEntity.class);
                    if (entity != null && "success".equals(entity.getResult())) {
                        SignHandler.onSignIn(entity, mISignListener);
                        getSupportDelegate().start(new EcBottomDelegate(),SINGLETASK);
                    }else{
                        SignHandler.onSignIn(null, mISignListener);
                    }
                }
            });
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
//        mUsrId.setText("KKSYS");
//        mPassword.setText("1234");
    }
}
