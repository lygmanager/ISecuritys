package net.bhtech.lygmanager.isecuritys.main.equip;

import android.content.DialogInterface;
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
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkClickListener;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.CompoundButtonGroup;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class WorksheetDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_layout)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_view)
    RecyclerView mRecyclerView = null;
//
//    @BindView(R.id.checkgroup)
//    CompoundButtonGroup checkgroup=null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;

    @BindView(R.id.text_title)
    TextView text_title=null;



    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_defect;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        //button_forward.setVisibility(View.VISIBLE);
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
        mRecyclerView.addOnItemTouchListener(WorsheetClickListener.create(this));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onSupportVisible() {
        mRefreshHandler.getWorksheetList(getContext());
    }

    @OnClick(R.id.button_forward)
    public void tipClick(View view) {
        WeakHashMap<String,Object> params=new WeakHashMap<>();
        params.put("orgno", "3");
        CompoundButtonGroup.CompoundButtonAlertDialog(getContext(), "getLxzbklxList", params, new CompoundButtonGroup.ConformListener() {
            @Override
            public void onConformClicked(String values,String texts) {
                text_title.setText(texts);

            }
        });

    }
}
