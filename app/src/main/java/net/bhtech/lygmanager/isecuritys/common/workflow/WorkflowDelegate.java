package net.bhtech.lygmanager.isecuritys.common.workflow;

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
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkDelegate;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2018/7/8.
 */

public class WorkflowDelegate extends BottomItemDelegate {

        @BindView(R.id.srl_layout)
        SwipeRefreshLayout mRefreshLayout = null;
        @BindView(R.id.rv_view)
        RecyclerView mRecyclerView = null;

        @BindView(R.id.button_forward)
        IconTextView button_forward=null;

        @BindView(R.id.text_title)
        TextView text_title=null;

        private String pkValue="";
        private String tableName="";


        private RefreshHandler mRefreshHandler = null;

        @Override
        public Object setLayout() {
        return R.layout.delegate_srl_rv;
    }

        @Override
        public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        text_title.setText("工作流处理信息");
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new WorkflowDataConverter());


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
    }

    @Override
    public void onSupportVisible() {
        if(tableName!=null&&!"".equals(tableName)&&pkValue!=null&&!"".equals(pkValue))
        {
            mRefreshHandler.getWorkFlowHistoryList(getContext(),tableName,pkValue);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("pkValue");
            tableName=args.getString("tableName");
        }
    }

    public static WorkflowDelegate create(String pkval,String tablename) {
        final Bundle args = new Bundle();
        args.putString("pkValue", pkval);
        args.putString("tableName", tablename);
        final WorkflowDelegate delegate = new WorkflowDelegate();
        delegate.setArguments(args);
        return delegate;
    }

}
