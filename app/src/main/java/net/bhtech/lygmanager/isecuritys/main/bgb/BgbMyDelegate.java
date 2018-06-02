package net.bhtech.lygmanager.isecuritys.main.bgb;

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
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class BgbMyDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_layout)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_view)
    RecyclerView mRecyclerView = null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;

    @BindView(R.id.text_title)
    TextView text_title=null;


    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bgb;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        text_title.setText("曝光版");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-plus}");
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("RMBGBMST@@BF_TYP,RMBGBMST@@BG_ADR");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbcstOption","BgbcstOption");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbsklOption","BgbsklOption");

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new BgbDataConverter());
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BgbBeanDelegate delegate = BgbBeanDelegate.create("-1");
                getParentDelegate().getSupportDelegate().start(delegate);
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
        final EcBottomDelegate ecBottomDelegate = getParentDelegate().getParentDelegate();
        mRecyclerView.addOnItemTouchListener(BgbClickListener.create(this));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onSupportVisible() {
        mRefreshHandler.getBgbList( getContext(),"MY");
    }




}
