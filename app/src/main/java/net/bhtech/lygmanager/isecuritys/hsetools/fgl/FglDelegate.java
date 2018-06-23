package net.bhtech.lygmanager.isecuritys.hsetools.fgl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.hsetools.tgl.TglBeanDelegate;
import net.bhtech.lygmanager.isecuritys.hsetools.tgl.TglDataConverter;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import java.util.Map;
import java.util.WeakHashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by zhangxinbiao on 2018/6/21.
 */

public class FglDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_layout)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_view)
    SwipeMenuRecyclerView mRecyclerView = null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;

    @BindView(R.id.text_title)
    TextView text_title=null;


    private RefreshHandler mRefreshHandler = null;

    private Context mContext=null;

    LatteDelegate DELEGATE;

    private UtusrEntity mUser=null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_swipemenu_rv;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mContext=getContext();
        DELEGATE=this;
        mUser= AccountManager.getSignInfo();
        text_title.setText("复工令");
//        button_forward.setVisibility(View.VISIBLE);
//        button_forward.setText("{fa-plus}");
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("HSEFGLMST@@VALID_STA,HSEFGLMST@@IS_JZ");

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new FglDataConverter());
//        button_forward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TglBeanDelegate aqgckBeanDelegate = TglBeanDelegate.create("-1");
//                getSupportDelegate().start(aqgckBeanDelegate);
//            }
//        });

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
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView TGL_ID=itemView.findViewById(R.id.FGL_NO);
                String id=TGL_ID.getText().toString();
                if(id!=null&&!"".equals(id)) {
                    FglBeanDelegate delegate = FglBeanDelegate.create(id);
                    DELEGATE.getSupportDelegate().start(delegate);
                }
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onSupportVisible() {
        WeakHashMap<String, Object> params=new WeakHashMap<>();
        params.put("usrid",mUser.getUserId());
        mRefreshHandler.getCommonList(getContext(),"getFglList",params);
    }

}
