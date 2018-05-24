package net.bhtech.lygmanager.isecuritys.main.mount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.TimeUtils;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.database.DatabaseManager;
import net.bhtech.lygmanager.database.MountEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.CxfRestClient;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.utils.callback.CallbackManager;
import net.bhtech.lygmanager.utils.callback.CallbackType;
import net.bhtech.lygmanager.utils.callback.IGlobalCallback;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class MountoffDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_recycler)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_recycler)
    RecyclerView mRecyclerView = null;

    @BindView(R.id.text_title)
    TextView text_title = null;
    @BindView(R.id.button_forward)
    IconTextView button_forward = null;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_recyclerview;
    }

    @OnClick(R.id.button_forward)
    void onClickScanQrCode() {
        startScanWithCheck(this);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("上岗到位");
        button_forward.setVisibility(View.VISIBLE);
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new MountDataConverter());
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String elcId) {
                        saveData(elcId);
                    }
                });
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
//        onTouchListener = new RecyclerTouchListener(this, recyclerView);
//        onTouchListener
//                .setIndependentViews(R.id.rowButton)
//                .setViewsToFade(R.id.rowButton)
//                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
//                    @Override
//                    public void onRowClicked(int position) {//item点击监听
//                        Toast.makeText(mContext, "Row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onIndependentViewClicked(int independentViewID, int position) {//button点击监听
//                        Toast.makeText(getApplicationContext(), "Button in row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setSwipeOptionViews(R.id.start, R.id.thumb, R.id.favorite)
//                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
//                    @Override
//                    public void onSwipeOptionClicked(int viewID, int position) {//侧拉出现的三个按钮监听事件
//                        String message = "";
//                        if (viewID == R.id.start) {
//                            message += "收 藏";
//                        } else if (viewID == R.id.thumb) {
//                            message += "点 赞";
//                        } else if (viewID == R.id.favorite) {
//                            message += "喜 欢";
//                        }
//                        message += " position-> " + (position + 1);
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        mRecyclerView.addOnItemTouchListener();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        DatabaseManager databaseManager=DatabaseManager.getInstance();
        List<MountEntity> ls=databaseManager.getDaoSession().getMountEntityDao().loadAll();
        mRefreshHandler.getMountList(ls);
    }

    public void saveData(String elcId){
        MountEntity entity=new MountEntity();
        entity.setMOUNT_DTM(TimeUtils.date2String(new Date()));
        entity.setELC_ID(elcId);
        DatabaseManager databaseManager=DatabaseManager.getInstance();
        databaseManager.getDaoSession().getMountEntityDao().insert(entity);
        List<MountEntity> ls=databaseManager.getDaoSession().getMountEntityDao().loadAll();
        mRefreshHandler.getMountList(ls);
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
                MountEntity entity= JSON.parseObject(o, MountEntity.class);
                if(entity!=null){
//                    DatabaseManager.getInstance().getDao().insert(entity);
                }
            }
        });
    }
}
