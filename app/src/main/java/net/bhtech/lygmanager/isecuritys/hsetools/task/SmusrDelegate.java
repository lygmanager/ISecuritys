package net.bhtech.lygmanager.isecuritys.hsetools.task;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.hsetools.fgl.FglBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkBeanDelegate;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.util.HashMap;
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

public class SmusrDelegate extends BottomItemDelegate {

    @BindView(R.id.srl_layout)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_view)
    SwipeMenuRecyclerView mRecyclerView = null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;

    @BindView(R.id.text_title)
    TextView text_title=null;
    private Context mContext=null;
    private UtusrEntity mUser=null;

    private String wType="";

    public static SmusrDelegate create(String wtype)
    {
        final Bundle args = new Bundle();
        args.putString("wtype", wtype);
        final SmusrDelegate delegate = new SmusrDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            wType=args.getString("wtype");
        }
    }

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_swipemenu_rv;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        String title="";
        if("1001".equals(wType))
        {
            title="超龄";
        }else if("1006".equals(wType))
        {
            title="黑名单";
        }

        text_title.setText(title+"员工自然信息表");
//        button_forward.setVisibility(View.VISIBLE);
//        button_forward.setText("{fa-plus}");
        mContext=getContext();
        mUser= AccountManager.getSignInfo();
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("SMUSRMST@@VEN_TYP,SMUSRMST@@USR_SEX,SMUSRMST@@VEN_TYP");

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new SmusrDataConverter());


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

        if("1001".equals(wType)) {
            mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
                @Override
                public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                    int width = getResources().getDimensionPixelSize(R.dimen.dp_72);
                    SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                    deleteItem.setText("列入黑名单")
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
                    BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) mRecyclerView.getOriginAdapter();
                    final MultipleItemEntity mentity = (MultipleItemEntity) baseQuickAdapter.getData().get(adapterPosition);
                    if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION && menuPosition == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("提醒");
                        builder.setMessage("是否将人员列入黑名单？");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                final Map<String, String> entity = new HashMap<>();
                                entity.put("USR_ID", mentity.getFieldString("USR_ID"));
                                entity.put("VALID_STA", "I");
                                entity.put("SM_STA", "2");
                                entity.put("QUA_STA", "00");
                                saveOrUpdate(entity);
                            }
                        });
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        Dialog noticeDialog = builder.create();
                        noticeDialog.show();
                    }

                }
            });
        }
    }

    private void saveOrUpdate(Map<String,String> entity)
    {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("saveOrUpdateSmusrMST")
                        .params("totaljson", JSONObject.toJSONString(entity))
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
                        mRefreshHandler.getSmusrList(getContext(),wType);
                        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
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
        mRefreshHandler.getSmusrList(getContext(),wType);
    }




}
