package net.bhtech.lygmanager.isecuritys.main.aqgck;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.AqgckEntity;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.index.OverrunItemClickListener;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.date.DatePickDialogUtil;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.recycler.MultipleRecyclerAdapter;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.EidtTextPopulWindow;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class AqgckBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;

    @BindView(R.id.AQ_NO)
    RightAndLeftEditText AQ_NO=null;
    @BindView(R.id.GC_ORG)
    RightAndLeftEditText GC_ORG=null;
    @BindView(R.id.BZ_NO)
    RightAndLeftEditText BZ_NO=null;
    @BindView(R.id.GC_NAM)
    RightAndLeftEditText GC_NAM=null;
    @BindView(R.id.GC_CST)
    RightAndLeftEditText GC_CST=null;
    @BindView(R.id.GC_DTM)
    RightAndLeftEditText GC_DTM=null;
    @BindView(R.id.BGC_ORG)
    RightAndLeftEditText BGC_ORG=null;
    @BindView(R.id.GC_RW)
    RightAndLeftEditText GC_RW=null;
    @BindView(R.id.GC_QY)
    RightAndLeftEditText GC_QY=null;
    @BindView(R.id.GC_SX)
    RightAndLeftEditText GC_SX=null;
    @BindView(R.id.GC_TYP)
    RightAndLeftEditText GC_TYP=null;
    @BindView(R.id.ORG_NO)
    RightAndLeftEditText ORG_NO=null;
    @BindView(R.id.WX_SHT)
    RightAndLeftEditText WX_SHT=null;
    @BindView(R.id.IS_JZ)
    RightAndLeftEditText IS_JZ=null;
    @BindView(R.id.JZ_CS)
    RightAndLeftEditText JZ_CS=null;
    @BindView(R.id.JZ_XD)
    RightAndLeftEditText JZ_XD=null;
    @BindView(R.id.FX_TYP)
    RightAndLeftEditText FX_TYP=null;
    @BindView(R.id.ZQX_SHT)
    RightAndLeftEditText ZQX_SHT=null;


    private ListPopupWindow listPopupWindow=null;
    private UtusrEntity mUser=null;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("AQ_NO");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_aqgckbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        button_commit.setVisibility(View.VISIBLE);
        button_commit.setText("{fa-plus}");
        final Context mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        Map<String,String[]> fieldOptions=LiemsMethods.init(mContext)
                .getFieldOption("AQGCKMST@@GC_ORG,AQGCKMST@@GC_CST,AQGCKMST@@GC_SX,AQGCKMST@@BGC_ORG,AQGCKMST@@GC_TYP,AQGCKMST@@IS_JZ,AQGCKMST@@FX_TYP");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        GC_DTM.setDatePick(this,sdf.format(new Date()),"DATE");
        GC_ORG.setPopulWindow(mContext,"AQGCKMST@@GC_ORG");
        GC_SX.setPopulWindow(mContext,"AQGCKMST@@GC_SX");
        BGC_ORG.setPopulWindow(mContext,"AQGCKMST@@BGC_ORG");
        GC_TYP.setPopulWindow(mContext,"AQGCKMST@@GC_TYP");
        IS_JZ.setPopulWindow(mContext,"AQGCKMST@@IS_JZ");
        FX_TYP.setPopulWindow(mContext,"AQGCKMST@@FX_TYP");

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AqgckEntity entity=new AqgckEntity();
                entity.setAQ_NO(AQ_NO.getEditTextInfo());
                entity.setGC_ORG(GC_ORG.getEditTextTagInfo());
                entity.setGC_NAM(GC_NAM.getEditTextTagInfo());
                entity.setGC_CST(GC_CST.getEditTextInfo());
                entity.setBZ_NO(BZ_NO.getEditTextInfo());
                entity.setGC_DTM(GC_DTM.getEditTextInfo());
                entity.setBGC_ORG(BGC_ORG.getEditTextTagInfo());
                entity.setGC_RW(GC_RW.getEditTextInfo());
                entity.setGC_QY(GC_QY.getEditTextInfo());
                entity.setGC_SX(GC_SX.getEditTextTagInfo());
                entity.setGC_TYP(GC_TYP.getEditTextTagInfo());
                entity.setORG_NO(ORG_NO.getEditTextInfo());

                entity.setWX_SHT(WX_SHT.getEditTextInfo());
                entity.setIS_JZ(IS_JZ.getEditTextTagInfo());
                entity.setJZ_CS(JZ_CS.getEditTextInfo());
                entity.setJZ_XD(JZ_XD.getEditTextInfo());
                entity.setFX_TYP(FX_TYP.getEditTextTagInfo());
                entity.setZQX_SHT(ZQX_SHT.getEditTextInfo());
                LatteLogger.d(JSONObject.toJSONString(entity));
                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateAQGCKMST")
                                .params("totaljson", JSONObject.toJSONString(entity))
                                .loader(mContext)
                                .build()
                                .post();
                obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                        @Override
                        public void onNext(String result) {
                        LatteLogger.d(result);
                            JSONObject jsonObject= JSONObject.parseObject(result);
                            LiemsResult rst=jsonObject.toJavaObject(LiemsResult.class);
                            if("success".equals(rst.getResult()))
                            {
                                if(rst.getPkValue()!=null&&!"".equals(rst.getPkValue()))
                                {
                                    AQ_NO.getEditText().setText(rst.getPkValue());
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
                AQ_NO.clearText();
                GC_ORG.clearText();
                GC_CST.clearText();
                GC_DTM.clearText();
                BGC_ORG.clearText();
                GC_RW.clearText();
                GC_QY.clearText();
                GC_SX.clearText();
                GC_TYP.clearText();
            }
        });
    }



    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return new DefaultHorizontalAnimator();
    }

    public static AqgckBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("AQ_NO", pictures);
        final AqgckBeanDelegate delegate = new AqgckBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getAQGCKList")
                            .params("aqno", pkValue)
                            .loader(this.getContext())
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String result) {
                    JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                    LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                    if ("success".equals(lr.getResult()) && !"0".equals(lr.getCount())) {
                        List<AqgckEntity> list = JSONArray.parseArray(lr.getRows().toString(), AqgckEntity.class);
                        for (AqgckEntity entity : list) {
                            AQ_NO.setEditTextInfo(entity.getAQ_NO());
                            GC_ORG.setEditTextInfo(entity.getGC_ORG());GC_ORG.setEditTextTagInfo(entity.getGC_ORG());
                            GC_NAM.setEditTextInfo(entity.getGC_NAM());GC_NAM.setEditTextTagInfo(entity.getGC_NAM());
                            GC_CST.setEditTextInfo(entity.getGC_CST());
                            BZ_NO.setEditTextInfo(entity.getBZ_NO());
                            GC_DTM.setEditTextInfo(entity.getGC_DTM());
                            BGC_ORG.setEditTextInfo(entity.getBGC_ORG());BGC_ORG.setEditTextTagInfo(entity.getBGC_ORG());
                            GC_RW.setEditTextInfo(entity.getGC_RW());
                            GC_QY.setEditTextInfo(entity.getGC_QY());
                            GC_SX.setEditTextInfo(entity.getGC_SX());
                            GC_TYP.setEditTextInfo(entity.getGC_TYP());GC_TYP.setEditTextTagInfo(entity.getGC_TYP());
                            ORG_NO.setEditTextInfo(entity.getORG_NO());

                            WX_SHT.setEditTextInfo(entity.getWX_SHT());
                            IS_JZ.setEditTextTagInfo(entity.getIS_JZ());
                            JZ_CS.setEditTextInfo(entity.getJZ_CS());
                            JZ_XD.setEditTextInfo(entity.getJZ_XD());
                            FX_TYP.setEditTextTagInfo(entity.getFX_TYP());
                            ZQX_SHT.setEditTextInfo(entity.getZQX_SHT());
                        }
                    }
                }
            });
        }else {
            if(mUser!=null) {
                GC_NAM.setEditTextInfo(mUser.getUsrNam());
                GC_NAM.setEditTextTagInfo(mUser.getUserId());
                ORG_NO.setEditTextInfo(mUser.getOrgNo());
                GC_CST.setEditTextInfo(mUser.getPlaNo());
                BZ_NO.setEditTextInfo(mUser.getCrwNo());
            }
        }
    }
}