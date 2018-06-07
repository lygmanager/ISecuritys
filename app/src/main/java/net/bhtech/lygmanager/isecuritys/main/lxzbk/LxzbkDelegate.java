package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckClickListener;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckDataConverter;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import butterknife.BindView;

/**
 * 领先指标卡业务列表
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LxzbkDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_lxzbk)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_lxzbk)
    RecyclerView mRecyclerView = null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;


    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_lxzbk;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("领先指标卡");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-plus}");

        LiemsMethods.init(getContext()).getLxzbklxOption("LXZBKLX");
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new LxzbkDataConverter());
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LxzbklinBeanDelegate delegate = LxzbklinBeanDelegate.create("-1");
                getSupportDelegate().start(delegate);
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
        mRecyclerView.addOnItemTouchListener(LxzbkClickListener.create(this));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onSupportVisible() {
        mRefreshHandler.getLxzbkList( getContext());
    }
}
