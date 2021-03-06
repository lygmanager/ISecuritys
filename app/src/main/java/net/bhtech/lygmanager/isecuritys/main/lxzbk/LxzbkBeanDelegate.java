package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.LxzbkEntity;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LxzbkBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.JCK_TYP)
    RightAndLeftEditText JCK_TYP=null;
    @BindView(R.id.JCK_DTM)
    RightAndLeftEditText JCK_DTM=null;
    @BindView(R.id.JCK_ADR)
    RightAndLeftEditText JCK_ADR=null;
    @BindView(R.id.JCK_DSC)
    RightAndLeftEditText JCK_DSC=null;
    @BindView(R.id.JCK_ID)
    RightAndLeftEditText JCK_ID=null;
    @BindView(R.id.JCK_NO)
    RightAndLeftEditText JCK_NO=null;

    @BindView(R.id.srl_jclin)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_jclin)
    RecyclerView mRecyclerView = null;
    private RefreshHandler mRefreshHandler = null;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("JCK_NO");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_lxzbkbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("领先指标卡明细");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        button_commit.setVisibility(View.VISIBLE);
        button_commit.setText("{fa-plus}");
        final Context mContext=this.getContext();
        mUser= AccountManager.getSignInfo();

        LiemsMethods.init(mContext).getLxzbklxOption("LXZBKLX");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        JCK_DTM.setDatePick(this,sdf.format(new Date()),"DATE");
        JCK_TYP.setPopulWindow(mContext,"LXZBKLX");
        LiemsMethods.init(getContext()).getFieldOption("SDZBJCKLIN@@VALID_STA");

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(JCK_TYP.getEditTextTagInfo()==null||"".equals(JCK_TYP.getEditTextTagInfo()))
                {

                    Toast.makeText(mContext, "请选择领先卡类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                WeakHashMap<String,Object> params=new WeakHashMap<>();

                params.put("JCK_NO",JCK_NO.getEditTextInfo());
                params.put("ORG_NO",mUser.getOrgNo());
                params.put("JCKUSR_ID",mUser.getUserId());
                params.put("JCKCST_NO",mUser.getPlaNo());
                params.put("CRW_NO",mUser.getCrwNo());
                params.put("JCK_TYP",JCK_TYP.getEditTextTagInfo());
                params.put("JCK_DTM",JCK_DTM.getEditTextInfo());
                params.put("TTK_NO","");
                params.put("JCK_ADR",JCK_ADR.getEditTextInfo());
                params.put("JCK_DSC",JCK_DSC.getEditTextInfo());
                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateLXZBKMST")
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
                                JCK_NO.setEditTextInfo(rst.getPkValue());
                                JCK_ID.setEditTextInfo(rst.getMsg());
                                mRefreshHandler.getLxzbklinList(getContext(),rst.getPkValue());
                                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JCK_NO.clearText();
                JCK_DTM.clearText();
                JCK_ADR.clearText();
                JCK_TYP.clearText();
                JCK_ID.clearText();
                JCK_DSC.clearText();
            }
        });


        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new LxzbklinDataConverter());
    }



    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getLXZBKDetail")
                            .params("jckno", pkValue)
                            .loader(this.getContext())
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String result) {
                    JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                    LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                    if ("success".equals(lr.getResult()) && !"0".equals(lr.getCount())) {
                        LxzbkEntity entity = JSONObject.parseObject(lr.getRows().toString(), LxzbkEntity.class);
                        if ( entity!=null) {
                            JCK_NO.setEditTextInfo(entity.getJCK_NO());
                            JCK_ID.setEditTextInfo(entity.getJCK_ID());
                            JCK_TYP.setEditTextTagInfo(entity.getJCK_TYP(),"LXZBKLX");
                            JCK_DTM.setEditTextInfo(entity.getJCK_DTM());
                            JCK_ADR.setEditTextInfo(entity.getJCK_ADR());
                            JCK_DSC.setEditTextInfo(entity.getJCK_DSC());
                            mRefreshHandler.getLxzbklinList(getContext(),entity.getJCK_NO());
                        }
                    }
                }
            });
        }
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
        mRecyclerView.addOnItemTouchListener(LxzbkbeanClickListener.create(this));
    }

    @Override
    public void onSupportVisible() {
        String jckno=JCK_NO.getEditTextInfo();
        if(jckno!=null&&!"".equals(jckno)) {
            mRefreshHandler.getLxzbklinList(getContext(),jckno);
        }
    }

    public static LxzbkBeanDelegate create(String pkval) {
        final Bundle args = new Bundle();
        args.putString("JCK_NO", pkval);
        final LxzbkBeanDelegate delegate = new LxzbkBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

}
