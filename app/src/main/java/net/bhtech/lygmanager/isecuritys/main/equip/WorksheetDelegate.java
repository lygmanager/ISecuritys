package net.bhtech.lygmanager.isecuritys.main.equip;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftTextView;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class WorksheetDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_defect)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_defect)
    RecyclerView mRecyclerView = null;


    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_defect;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new WorksheetDataConverter());

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
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
//        mRefreshHandler.getDefectList(defect_elcid.getText().toString(),getContext());
    }

    @Override
    public void onSupportVisible() {
        Fragment fragment=getParentFragment();
        RightAndLeftTextView rltv=fragment.getView().findViewById(R.id.ELC_ID);
        String value=rltv.getRightText();
        if(value!=null&&!"".equals(value)) {
            if(this.isSupportVisible()) {
                mRefreshHandler.getWorksheetList(value, getContext());
            }
        }
    }
}
