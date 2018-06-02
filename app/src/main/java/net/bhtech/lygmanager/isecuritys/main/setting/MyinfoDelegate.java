package net.bhtech.lygmanager.isecuritys.main.setting;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.BaseDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.sign.SignInDelegate;
import net.bhtech.lygmanager.net.appupdate.AppVersionEntity;
import net.bhtech.lygmanager.net.appupdate.UpdateManager;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class MyinfoDelegate extends BottomItemDelegate{

    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.userId)
    RightAndLeftEditText userId=null;

    @BindView(R.id.usrNam)
    RightAndLeftEditText usrNam=null;

    @BindView(R.id.orgName)
    RightAndLeftEditText orgName=null;

    @BindView(R.id.cstName)
    RightAndLeftEditText cstName=null;

    @BindView(R.id.position)
    RightAndLeftEditText position=null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_myinfo;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final Context mContext = getContext();
        text_title.setText("我的资料");
        UtusrEntity utusrEntity = AccountManager.getSignInfo();
        userId.setEditTextInfo(utusrEntity.getUserId());
        usrNam.setEditTextInfo(utusrEntity.getUsrNam());
        orgName.setEditTextInfo(utusrEntity.getOrgName());
        cstName.setEditTextInfo(utusrEntity.getCstName());
        position.setEditTextInfo(utusrEntity.getPosition());
    }


}
