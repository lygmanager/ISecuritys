package net.bhtech.lygmanager.isecuritys.hsetools.gfxzy;

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
import net.bhtech.lygmanager.isecuritys.hsetools.gfxjd.GfxjdBeanDelegate;
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
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class GfxzyDelegate extends BottomItemDelegate {

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
        text_title.setText("高风险作业许可证");
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("GFXZYXKMST@@VALID_STA,GFXZYXKMST@@XK_JB");
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new GfxzyDataConverter());

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
//        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                TextView ZY_NO=itemView.findViewById(R.id.ZY_NO);
//                String id=ZY_NO.getText().toString();
//                if(id!=null&&!"".equals(id)) {
//                    TglBeanDelegate delegate = TglBeanDelegate.create(id);
//                    DELEGATE.getSupportDelegate().start(delegate);
//                }
//            }
//        });


        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_72);
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                deleteItem.setText("生成监督记录")
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
                    if(!"0".equals(entity.getField("JD_NUM"))&&!"".equals(entity.getField("JD_NUM")))
                    {
                        String id=entity.getField("JD_NUM");
                        if(id!=null&&!"".equals(id)) {
                            Toast.makeText(mContext, "现场监督已生成，跳转到该记录！", Toast.LENGTH_SHORT).show();
                            GfxjdBeanDelegate delegate = GfxjdBeanDelegate.create(id);
                            DELEGATE.getSupportDelegate().start(delegate);
                        }
                        return;
                    }
//                    if("02".equals(entity.getField("VALID_STA_VAL"))) {
//                        if(!mUser.getUserId().equals(entity.getField("TG_USR")))
//                        {
//                            Toast.makeText(mContext, "项目承包人才可生成复工申请！", Toast.LENGTH_LONG).show();
//                            return;
//                        }

                        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        WeakHashMap<String, Object> params = new WeakHashMap<>();
                        params.put("JD_NO", "-1");
                        params.put("ZY_NO", entity.getField("ZY_NO"));
                        params.put("ORG_NO", mUser.getOrgNo());
                        params.put("FSTUSR_ID", mUser.getUserId());
                        params.put("FSTUSR_DTM", sdf.format(new Date()));
                        params.put("PLA_NO", entity.getField("ZY_DW"));
                        params.put("CRW_NO", entity.getField("CRW_NO"));
                        params.put("JD_USR", mUser.getUserId());
                        params.put("JD_ENDUSR", mUser.getUserId());
                        params.put("VALID_STA", "00");
                        Observable<String> obj=
                                RxRestClient.builder()
                                        .url("saveOrUpdateGfxjdMST")
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
                                    if(rst.getPkValue()!=null&&!"".equals(rst.getPkValue()))
                                    {
                                        Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                                        GfxjdBeanDelegate delegate = GfxjdBeanDelegate.create(rst.getPkValue());
                                        DELEGATE.getSupportDelegate().start(delegate);
                                    }
                                }else{
                                    Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                    }else{
//                        Toast.makeText(mContext, "请完成停工令审批才可生成复工申请", Toast.LENGTH_SHORT).show();
//                    }
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
        mRefreshHandler.getCommonList(getContext(),"getGfxzyxkList",null);
    }




}
