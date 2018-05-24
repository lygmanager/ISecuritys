package net.bhtech.lygmanager.isecuritys.main.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class TaskDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R.id.icon_index_scan)
    IconTextView mIconScan = null;

    private RefreshHandler mRefreshHandler = null;

    @OnClick(R.id.icon_index_scan)
    void onClickScanQrCode() {
        startScanWithCheck(this);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_task;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new OverrunDataConverter());

//        mSearchView.setOnFocusChangeListener(this);
    }
}
