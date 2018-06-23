package net.bhtech.lygmanager.isecuritys.hsetools.tgl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckClickListener;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckDataConverter;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftTextView;
import net.bhtech.lygmanager.utils.log.LatteLogger;

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

public class TglDelegate extends BottomItemDelegate {

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
        text_title.setText("停工令");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-plus}");
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("HSETGLMST@@VALID_STA");

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new TglDataConverter());
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TglBeanDelegate aqgckBeanDelegate = TglBeanDelegate.create("-1");
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
//        mRecyclerView.addOnItemTouchListener(TglClickListener.create(this));
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TextView TGL_ID=itemView.findViewById(R.id.TGL_NO);
                String id=TGL_ID.getText().toString();
                if(id!=null&&!"".equals(id)) {
                    TglBeanDelegate delegate = TglBeanDelegate.create(id);
                    DELEGATE.getSupportDelegate().start(delegate);
                }
            }
        });


        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_72);
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                deleteItem.setText("生成复工令")
                        .setBackground(R.color.badgeColor)
                        .setWidth(width)
                        .setHeight(MATCH_PARENT);
                rightMenu.addMenuItem(deleteItem);
            }
        });

        mRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                BaseQuickAdapter baseQuickAdapter=(BaseQuickAdapter) mRecyclerView.getOriginAdapter();
                final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(adapterPosition);
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION&&menuPosition==0) {
                    if("02".equals(entity.getField("VALID_STA_VAL"))) {
                        WeakHashMap<String, Object> params = new WeakHashMap<>();
                        params.put("FGL_NO", "-1");
                        params.put("TGL_NO", entity.getField("TGL_NO"));
                        params.put("CBSHSE_MAN", mUser.getUserId());
                        params.put("ORG_NO", mUser.getOrgNo());
                        params.put("TG_CBS", entity.getField("TG_CBS"));
                        params.put("TG_QY", entity.getField("TG_QY"));
                        params.put("CBSPRJ_MAN", entity.getField("TG_USR"));
                        Observable<String> obj=
                                RxRestClient.builder()
                                        .url("saveOrUpdateFglMST")
                                        .params("totaljson", JSONObject.toJSONString(params))
                                        .loader(mContext)
                                        .build()
                                        .post();
                        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                            @Override
                            public void onNext(String result) {
                                JSONObject jsonObject= JSONObject.parseObject(result);
                                LiemsResult rst=jsonObject.toJavaObject(LiemsResult.class);
                                if("success".equals(rst.getResult()))
                                {
                                    LatteLogger.d(result);
                                    if(rst.getPkValue()!=null&&!"".equals(rst.getPkValue()))
                                    {
                                        Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                                        FglBeanDelegate delegate = FglBeanDelegate.create(rst.getPkValue());
                                        DELEGATE.getSupportDelegate().start(delegate);
                                    }
                                }else{
                                    Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(mContext, "请完成停工令审批才可生成复工令", Toast.LENGTH_SHORT).show();
                    }
                } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                    Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
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
        mRefreshHandler.getTglList(getContext());
    }




}
