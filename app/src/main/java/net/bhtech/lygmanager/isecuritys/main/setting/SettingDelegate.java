package net.bhtech.lygmanager.isecuritys.main.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.delegates.BaseDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.sign.ISignListener;
import net.bhtech.lygmanager.isecuritys.sign.SignHandler;
import net.bhtech.lygmanager.isecuritys.sign.SignInDelegate;
import net.bhtech.lygmanager.net.RestCreator;
import net.bhtech.lygmanager.net.appupdate.AppVersionEntity;
import net.bhtech.lygmanager.net.appupdate.UpdateManager;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;

import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class SettingDelegate extends BottomItemDelegate{

    @BindView(R.id.btn_sign_out)
    TextView btn_sign_out=null;

    @BindView(R.id.btn_app_update)
    TextView btn_app_update=null;



    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final Context mContext=getContext();
        final BaseDelegate delegate=this.getParentDelegate();
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.setSignState(false);
                getParentDelegate().getSupportDelegate().start(new SignInDelegate());
            }
        });

        btn_app_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
                String url=BASE_URL.replaceAll("webservice/","apkfile/update.json");
                Observable<String> obj =
                        RxRestClient.builder()
                                .url(url)
                                .loader(mContext)
                                .build()
                                .post();
                obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
                    @Override
                    public void onNext(String result) {
                        if(result!=null&&!"".equals(result)) {
                            AppVersionEntity entity=JSONObject.parseObject(result, AppVersionEntity.class);
                            UpdateManager manager=new UpdateManager(delegate,getContext());
                            manager.checkUpdate(entity);
                        }
                    }
                });
            }
        });

    }
}
