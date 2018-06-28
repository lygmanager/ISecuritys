package net.bhtech.lygmanager.isecuritys.hsetools.tgl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.joanzapata.iconify.widget.IconTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;

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

public class TglBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;
    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.TGL_NO)
    RightAndLeftEditText TGL_NO=null;
    @BindView(R.id.TGL_ID)
    RightAndLeftEditText TGL_ID=null;
    @BindView(R.id.TG_SHT)
    RightAndLeftEditText TG_SHT=null;
    @BindView(R.id.TG_CBS)
    RightAndLeftEditText TG_CBS=null;
    @BindView(R.id.TG_USR)
    RightAndLeftEditText TG_USR=null;
    @BindView(R.id.TG_QY)
    RightAndLeftEditText TG_QY=null;
    @BindView(R.id.TG_WT)
    RightAndLeftEditText TG_WT=null;
    @BindView(R.id.TG_CS)
    RightAndLeftEditText TG_CS=null;
    @BindView(R.id.TG_BZ)
    RightAndLeftEditText TG_BZ=null;
    @BindView(R.id.TG_DTM)
    RightAndLeftEditText TG_DTM=null;
    @BindView(R.id.PZUSR_ID)
    RightAndLeftEditText PZUSR_ID=null;
    @BindView(R.id.PZ_DTM)
    RightAndLeftEditText PZ_DTM=null;

    private TglBeanDelegate thisdelegate=this;

    @BindView(R.id.ll_equip)
    LinearLayout ll_equip=null;

    protected Context mContext=null;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("TGL_NO");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_tglbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("停工令明细");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        button_commit.setVisibility(View.VISIBLE);
        button_commit.setText("{fa-plus}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        TG_DTM.setDatePick(this,sdf.format(new Date()),"DATE");
        TG_CBS.setVdvenDialog(mContext,TG_USR);
        TG_USR.setCstUserDialog(mContext);
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIsEmpty(mContext,ll_equip))
                {
                    return;
                }
                WeakHashMap<String,Object> params=new WeakHashMap<>();
                params.put("TGL_NO",TGL_NO.getEditTextInfo());
                params.put("TG_SHT",TG_SHT.getEditTextInfo());
                params.put("TG_CBS",TG_CBS.getEditTextTagInfo());
                params.put("TG_USR",TG_USR.getEditTextTagInfo());
                params.put("TG_QY",TG_QY.getEditTextInfo());
                params.put("TG_WT",TG_WT.getEditTextInfo());
                params.put("TG_CS",TG_CS.getEditTextInfo());
                params.put("TG_BZ",TG_BZ.getEditTextTagInfo());
                params.put("TG_DTM",TG_DTM.getEditTextInfo());
//                params.put("PZUSR_ID",PZUSR_ID.getEditTextTagInfo());
//                params.put("PZ_DTM",PZ_DTM.getEditTextInfo());
                params.put("ORG_NO",mUser.getOrgNo());

                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateTglMST")
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
                                    TGL_NO.getEditText().setText(rst.getPkValue());
                                    if(rst.getMsg()!=null&&!"".equals(rst.getMsg())) {
                                        TGL_ID.setEditTextInfo(rst.getMsg());
                                    }
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
                TGL_NO.clearText();
                TGL_ID.clearText();
                TG_SHT.clearText();
                TG_CBS.clearText();
                TG_USR.clearText();
                TG_QY.clearText();
                TG_WT.clearText();
                TG_CS.clearText();
                TG_BZ.setEditTextInfo(mUser.getUsrNam());
                TG_BZ.setEditTextTagInfo(mUser.getUserId());
                TG_DTM.setEditTextInfo(sdf.format(new Date()));
                PZUSR_ID.clearText();
                PZ_DTM.clearText();
            }
        });
    }

    @OnLongClick(R.id.iView)
    public boolean setiView(View view)
    {
        if(TGL_NO.getEditTextInfo()!=null&&!"".equals(TGL_NO.getEditTextInfo())) {
            PictureSelector
                    .create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .compress(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
        return true;
    }

    @OnClick(R.id.iView)
    public void openFullView(View view)
    {
        if(TGL_NO.getEditTextInfo()!=null&&!"".equals(TGL_NO.getEditTextInfo()))
        {
//            if("A".equals(PICTUREA.getEditTextInfo())) {
//                FullimageDelegate delegate = FullimageDelegate.create("AQ_PICTUREA_" + TGL_NO.getEditTextInfo(),"AQGCKMST");
//                this.getSupportDelegate().start(delegate);
//            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    if(selectList!=null&&selectList.size()>0) {
//                        Glide.with(_mActivity).load(selectList.get(0).getCompressPath()).into(iView);
//                        if(TGL_NO.getEditTextInfo()!=null&&!"".equals(TGL_NO.getEditTextInfo())) {
//                            LiemsMethods.init(mContext).upLoadFile(selectList.get(0).getCompressPath(), "AQGCKMST", "AQ_PICTUREA_"+TGL_NO.getEditTextInfo()+".JPEG");
//                            PICTUREA.setEditTextInfo("A");
//                            Toast.makeText(mContext,"图片上传成功！", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(mContext,"请先保存曝光板内容再添加照片！", Toast.LENGTH_SHORT).show();
//                        }
//                    }
                    break;
            }
        }
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return new DefaultHorizontalAnimator();
    }

    public static TglBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("TGL_NO", pictures);
        final TglBeanDelegate delegate = new TglBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getTglDetail")
                            .params("pkValue", pkValue)
                            .loader(this.getContext())
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String result) {
                    JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                    LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                    if ("success".equals(lr.getResult()) && !"0".equals(lr.getCount())) {
                        JSONObject entity = JSONObject.parseObject(lr.getRows().toString());
                        if ( entity!=null) {
                            TGL_NO.setEditTextInfo(entity.getString("TGL_NO"));
                            TGL_ID.setEditTextInfo(entity.getString("TGL_ID"));
                            TG_SHT.setEditTextInfo(entity.getString("TG_SHT"));
                            TG_CBS.setEditTextTagInfo(entity.getString("TG_CBS"));
                            TG_CBS.setEditTextInfo(entity.getString("TG_CBS_NAM"));
                            TG_USR.setEditTextInfo(entity.getString("TG_USR"));
                            TG_USR.setEditTextInfo(entity.getString("TG_USR_NAM"));
                            TG_QY.setEditTextInfo(entity.getString("TG_QY"));
                            TG_WT.setEditTextInfo(entity.getString("TG_WT"));
                            TG_CS.setEditTextInfo(entity.getString("TG_CS"));
                            TG_BZ.setEditTextTagInfo(entity.getString("TG_BZ"));
                            TG_BZ.setEditTextInfo(entity.getString("TG_BZ_NAM"));
                            TG_DTM.setEditTextInfo(entity.getString("TG_DTM"));
                            PZUSR_ID.setEditTextTagInfo(entity.getString("PZUSR_ID"));
                            PZUSR_ID.setEditTextInfo(entity.getString("PZUSR_NAM"));
                            PZ_DTM.setEditTextInfo(entity.getString("PZ_DTM"));
                        }
                    }
                }
            });
        }else {
            TG_BZ.setEditTextInfo(mUser.getUsrNam());
            TG_BZ.setEditTextTagInfo(mUser.getUserId());
        }
    }
}
