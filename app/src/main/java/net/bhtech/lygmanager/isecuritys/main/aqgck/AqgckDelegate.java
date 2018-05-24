package net.bhtech.lygmanager.isecuritys.main.aqgck;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.equip.DefectDataConverter;
import net.bhtech.lygmanager.isecuritys.main.index.OverrunItemClickListener;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftTextView;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class AqgckDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_aqgck)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_aqgck)
    RecyclerView mRecyclerView = null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;


    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_aqgck;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-plus}");
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new AqgckDataConverter());
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AqgckBeanDelegate aqgckBeanDelegate = AqgckBeanDelegate.create("-1");
                getSupportDelegate().start(aqgckBeanDelegate);
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
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(AqgckClickListener.create(this));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onSupportVisible() {
        mRefreshHandler.getAqgcktList( getContext());
    }




}
