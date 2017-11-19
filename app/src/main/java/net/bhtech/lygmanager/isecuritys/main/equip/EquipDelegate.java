package net.bhtech.lygmanager.isecuritys.main.equip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.database.EqelcEntity;
import net.bhtech.lygmanager.delegates.bottom.BaseBottomDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomTabBean;
import net.bhtech.lygmanager.delegates.bottom.ItemBuilder;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.CxfRestClient;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.ui.tag.RightAndLeftTextView;
import net.bhtech.lygmanager.utils.callback.CallbackManager;
import net.bhtech.lygmanager.utils.callback.CallbackType;
import net.bhtech.lygmanager.utils.callback.IGlobalCallback;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class EquipDelegate extends BaseBottomDelegate {

    @BindView(R.id.text_title)
    TextView text_title = null;
    @BindView(R.id.button_forward)
    IconTextView button_forward = null;

    @BindView(R.id.ELC_ID)
    RightAndLeftTextView ELC_ID = null;
    @BindView(R.id.ELC_NAM)
    RightAndLeftTextView ELC_NAM = null;
    @BindView(R.id.ETP_NO)
    RightAndLeftTextView ETP_NO = null;
    @BindView(R.id.GGXH)
    RightAndLeftTextView GGXH = null;
    @BindView(R.id.SCCJ)
    RightAndLeftTextView SCCJ = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_equip;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        super.onBindView(savedInstanceState,rootView);
        text_title.setText("设备参数");
        button_forward.setVisibility(View.VISIBLE);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String elcId) {
                        initData(elcId);
                    }
                });
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "缺陷"), new DefectDelegate());
        items.put(new BottomTabBean("{fa-sort}", "工作票"), new WorksheetDelegate());
        return builder.addItems(items).build();
    }

    @OnClick(R.id.button_forward)
    void onClickScanQrCode() {
        startScanWithCheck(this);
    }
    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#0099FF");
    }

    public void initData(String elcId){
        Observable<String> obj= CxfRestClient.builder()
                .url("getElcInfo")
                .params("arg0", "3")
                .params("arg1", elcId)
                .build()
                .post();
        obj.subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                EqelcEntity entity= JSON.parseObject(o, EqelcEntity.class);
                if(entity!=null){
                    ELC_ID.setRightText(entity.getELC_ID());
                    ELC_NAM.setRightText(entity.getELC_NAM());
                    ETP_NO.setRightText(entity.getETP_NO());
                    GGXH.setRightText(entity.getGGXH());
                    SCCJ.setRightText(entity.getSCCJ());
                }
            }
        });
    }
}
