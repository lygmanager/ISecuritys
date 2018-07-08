package net.bhtech.lygmanager.isecuritys.hsetools.gfxjd;

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
import net.bhtech.lygmanager.isecuritys.hsetools.fgl.FglBeanDelegate;
import net.bhtech.lygmanager.isecuritys.hsetools.gfxzy.GfxzyDataConverter;
import net.bhtech.lygmanager.isecuritys.hsetools.tgl.TglBeanDelegate;
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
 * Created by zhangxinbiao on 2017/11/26.
 */

public class GfxjdDelegate extends BottomItemDelegate {

    private String pkValue="";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("ZY_NO");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_swipemenu_rv;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mContext=getContext();
        DELEGATE=this;
        mUser= AccountManager.getSignInfo();
        text_title.setText("高风险作业监督");
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("SFGFXJDMST@@VALID_STA");
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new GfxjdDataConverter());

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
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView JD_NO=itemView.findViewById(R.id.JD_NO);
                String id=JD_NO.getText().toString();
                if(id!=null&&!"".equals(id)) {
                    GfxjdBeanDelegate delegate = GfxjdBeanDelegate.create(id);
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
        params.put("ZY_NO",pkValue);
        mRefreshHandler.getCommonList(getContext(),"getGfxjdList",params);
    }

    public static GfxjdDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("ZY_NO", pictures);
        final GfxjdDelegate delegate = new GfxjdDelegate();
        delegate.setArguments(args);
        return delegate;
    }


}
