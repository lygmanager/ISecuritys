package net.bhtech.lygmanager.isecuritys.hsetools.gfxjd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.common.FullimageDelegate;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkbeanClickListener;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbklinDataConverter;
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
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class GfxjdBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;
    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.JD_NO)
    RightAndLeftEditText JD_NO=null;
    @BindView(R.id.PLA_NO)
    RightAndLeftEditText PLA_NO=null;
    @BindView(R.id.CRW_NO)
    RightAndLeftEditText CRW_NO=null;
    @BindView(R.id.ZY_ID)
    RightAndLeftEditText ZY_ID=null;
    @BindView(R.id.JD_USR_NAM)
    RightAndLeftEditText JD_USR_NAM=null;
    @BindView(R.id.JD_ITEM)
    RightAndLeftEditText JD_ITEM=null;
    @BindView(R.id.JD_DESC)
    RightAndLeftEditText JD_DESC=null;
    @BindView(R.id.JD_ADR)
    RightAndLeftEditText JD_ADR=null;
    @BindView(R.id.START_DTM)
    RightAndLeftEditText START_DTM=null;
    @BindView(R.id.END_DTM)
    RightAndLeftEditText END_DTM=null;
    @BindView(R.id.JD_REPORT)
    RightAndLeftEditText JD_REPORT=null;
    @BindView(R.id.JD_DPTUSR)
    RightAndLeftEditText JD_DPTUSR=null;


    @BindView(R.id.srl_jclin)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_jclin)
    RecyclerView mRecyclerView = null;
    private RefreshHandler mRefreshHandler = null;

    private GfxjdBeanDelegate thisdelegate=this;

    protected Context mContext=null;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("JD_NO");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_gfxjdbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("高风险现场监督");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        START_DTM.setDatePick(this,null,"DATE");
        END_DTM.setDatePick(this,null,"DATE");

        LiemsMethods.init(mContext)
                .getFieldOption("SFGFXJDLIN@@JDLIN_JL");

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeakHashMap<String,Object> params=new WeakHashMap<>();
                params.put("JD_NO",JD_NO.getEditTextInfo());
                params.put("JD_ITEM",JD_ITEM.getEditTextInfo());
                params.put("JD_DESC",JD_DESC.getEditTextInfo());
                params.put("JD_ADR",JD_ADR.getEditTextInfo());
                params.put("START_DTM",START_DTM.getEditTextInfo());
                params.put("END_DTM",END_DTM.getEditTextInfo());

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
                                    JD_NO.getEditText().setText(rst.getPkValue());
                                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });

        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new GfxjdlinDataConverter());
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
        mRecyclerView.addOnItemTouchListener(GfxjdbeanClickListener.create(this));
    }

    @Override
    public void onSupportVisible() {
        String jckno=JD_NO.getEditTextInfo();
        if(jckno!=null&&!"".equals(jckno)) {

            WeakHashMap<String, Object> params=new WeakHashMap<>();
            params.put("JD_NO",jckno);
            mRefreshHandler.getCommonList(mContext,"getGfxjdlinList",params);
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return new DefaultHorizontalAnimator();
    }

    public static GfxjdBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("JD_NO", pictures);
        final GfxjdBeanDelegate delegate = new GfxjdBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        initRefreshLayout();
        initRecyclerView();
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getGfxjdList")
                            .params("orgno", mUser.getOrgNo())
                            .params("wherelimit", " {\"JD_NO\":\""+pkValue+"\"}")
                            .loader(this.getContext())
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String result) {
                    JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                    LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                    if ("success".equals(lr.getResult()) && !"0".equals(lr.getCount())) {

                        JSONArray jsonArray = JSONArray.parseArray(lr.getRows().toString());
                        if(jsonArray!=null&&jsonArray.size()>0) {
                            JSONObject entity=jsonArray.getJSONObject(0);
                            if (entity != null) {
                                JD_NO.setEditTextInfo(entity.getString("JD_NO"));
                                PLA_NO.setEditTextInfo(entity.getString("PLA_NAM"));
                                CRW_NO.setEditTextInfo(entity.getString("CRW_NAM"));
                                ZY_ID.setEditTextInfo(entity.getString("ZY_ID"));
                                JD_USR_NAM.setEditTextInfo(entity.getString("JD_USR_NAM"));
                                JD_ITEM.setEditTextInfo(entity.getString("JD_ITEM"));
                                JD_DESC.setEditTextInfo(entity.getString("JD_DESC"));
                                JD_ADR.setEditTextInfo(entity.getString("JD_ADR"));
                                START_DTM.setEditTextInfo(entity.getString("START_DTM"));
                                END_DTM.setEditTextInfo(entity.getString("END_DTM"));
                                JD_REPORT.setEditTextInfo(entity.getString("JD_REPORT"));
                                JD_DPTUSR.setEditTextInfo(entity.getString("JD_DPTUSR_NAM"));
                                WeakHashMap<String, Object> params=new WeakHashMap<>();
                                params.put("JD_NO",JD_NO.getEditTextInfo());
                                mRefreshHandler.getCommonList(mContext,"getGfxjdlinList",params);
                            }
                        }
                    }else {
                        Toast.makeText(mContext, lr.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }
}
