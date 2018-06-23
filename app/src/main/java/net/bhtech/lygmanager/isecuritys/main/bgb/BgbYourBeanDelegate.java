package net.bhtech.lygmanager.isecuritys.main.bgb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import net.bhtech.lygmanager.isecuritys.common.EditImageDialog;
import net.bhtech.lygmanager.isecuritys.common.FullimageDelegate;
import net.bhtech.lygmanager.isecuritys.dialog.ConformListener;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class BgbYourBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;
    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.BGB_NO)
    RightAndLeftEditText BGB_NO=null;
    @BindView(R.id.CST_NO)
    RightAndLeftEditText CST_NO=null;
    @BindView(R.id.BG_ADR)
    RightAndLeftEditText BG_ADR=null;
    @BindView(R.id.BF_TYP)
    RightAndLeftEditText BF_TYP=null;
    @BindView(R.id.BG_NOT)
    RightAndLeftEditText BG_NOT=null;
    @BindView(R.id.JC_DTM)
    RightAndLeftEditText JC_DTM=null;
    @BindView(R.id.DO_DTM)
    RightAndLeftEditText DO_DTM=null;
    @BindView(R.id.JC_TYP)
    RightAndLeftEditText JC_TYP=null;
    @BindView(R.id.KH_NUM)
    RightAndLeftEditText KH_NUM=null;
    @BindView(R.id.PLAN_DTM)
    RightAndLeftEditText PLAN_DTM=null;
    @BindView(R.id.GLCST_NO)
    RightAndLeftEditText GLCST_NO=null;
    @BindView(R.id.JCUSR_ID)
    RightAndLeftEditText JCUSR_ID=null;
    @BindView(R.id.ZGSKL_NO)
    RightAndLeftEditText ZGSKL_NO=null;
    @BindView(R.id.SKL_NO)
    RightAndLeftEditText SKL_NO=null;
    @BindView(R.id.GLUSR_ID)
    RightAndLeftEditText GLUSR_ID=null;
    @BindView(R.id.PICTUREA)
    RightAndLeftEditText PICTUREA=null;
    @BindView(R.id.PICTUREB)
    RightAndLeftEditText PICTUREB=null;
    @BindView(R.id.iView)
    ImageView iView=null;
    @BindView(R.id.iViewB)
    ImageView iViewB=null;

    @BindView(R.id.lineiViewA)
    LinearLayout lineiViewA=null;
    @BindView(R.id.lineiViewB)
    LinearLayout lineiViewB=null;

    protected Context mContext=null;
    private BgbYourBeanDelegate thisdelegate=this;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("BGB_NO");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_bgbbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("待整改曝光板明细");
        button_forward.setVisibility(View.GONE);
        button_forward.setText("{fa-save}");
        button_commit.setVisibility(View.GONE);
        button_commit.setText("{fa-plus}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("RMBGBMST@@BF_TYP,RMBGBMST@@BG_ADR");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbzgcstOption","BgbzgcstOption");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbcstOption","BgbcstOption");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbjcnrOption","BgbjcnrOption");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String today=sdf.format(new Date());
        JC_DTM.setDatePick(this,today,"DATE");
        PLAN_DTM.setDatePick(this,today,"DATE");
        DO_DTM.setDatePick(this,null,"DATE");
        JC_TYP.setPopulWindow(mContext,"BgbjcnrOption");
        BF_TYP.setPopulWindow(mContext,"RMBGBMST@@BF_TYP");
        BG_ADR.setPopulWindow(mContext,"RMBGBMST@@BG_ADR");
        CST_NO.setPopulWindow(mContext,"BgbzgcstOption",ZGSKL_NO,"getBgbzgcstsklOption","BgbzgcstsklOption");
        ZGSKL_NO.setPopulWindow(mContext,"BgbzgcstsklOption",GLUSR_ID,"getBgbzgcstUserOption","BgbzgcstUserOption");
        GLCST_NO.setPopulWindow(mContext,"BgbcstOption",SKL_NO,"getBgbcstsklOption","BgbcstsklOption");
        SKL_NO.setPopulWindow(mContext,"BgbcstsklOption");
        GLUSR_ID.setPopulWindow(mContext,"BgbzgcstUserOption");

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> entity=new HashMap<>();
                entity.put("BGB_NO",BGB_NO.getEditTextInfo());
                entity.put("CST_NO",CST_NO.getEditTextTagInfo());
                entity.put("BG_ADR",BG_ADR.getEditTextTagInfo());
                entity.put("BF_TYP",BF_TYP.getEditTextTagInfo());
                entity.put("BG_NOT",BG_NOT.getEditTextInfo());
                entity.put("JC_DTM",JC_DTM.getEditTextInfo());
                if(DO_DTM.getEditTextInfo()!=null&&!"".equals(DO_DTM.getEditTextInfo())) {
                    entity.put("DO_DTM", DO_DTM.getEditTextInfo());
                    entity.put("YSUSR_ID", mUser.getUserId());
                }
                entity.put("JC_TYP",JC_TYP.getEditTextTagInfo());
                entity.put("KH_NUM",KH_NUM.getEditTextInfo());
                entity.put("PLAN_DTM",PLAN_DTM.getEditTextInfo());
                entity.put("GLCST_NO",GLCST_NO.getEditTextTagInfo());
                entity.put("GLUSR_ID",GLUSR_ID.getEditTextTagInfo());
                entity.put("SKL_NO",SKL_NO.getEditTextTagInfo());
                entity.put("KH_FS","1");
                entity.put("ORG_NO",mUser.getOrgNo());
                entity.put("JCUSR_ID",mUser.getUserId());
                entity.put("JCST_NO",mUser.getCstNo());
                entity.put("JLUSR_ID",mUser.getUserId());
                entity.put("JLUSR_DTM",today);
                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateBgbMST")
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
                                    BGB_NO.getEditText().setText(rst.getPkValue());
                                    lineiViewA.setVisibility(View.VISIBLE);
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
                BGB_NO.clearText();
                CST_NO.clearText();
                BG_ADR.clearText();
                BF_TYP.clearText();
                BG_NOT.clearText();
                JC_DTM.clearText();
                JC_TYP.clearText();
                DO_DTM.clearText();
                KH_NUM.clearText();
                GLUSR_ID.clearText();
                PLAN_DTM.clearText();
                GLCST_NO.clearText();
                SKL_NO.clearText();
                PICTUREA.clearText();
                PICTUREB.clearText();
                iView.setImageBitmap(null);
                iViewB.setImageBitmap(null);
            }
        });
    }

    @OnClick(R.id.iView)
    public void openFullView(View view)
    {
        if(BGB_NO.getEditTextInfo()!=null&&!"".equals(BGB_NO.getEditTextInfo()))
        {
            if(!"".equals(PICTUREA.getEditTextInfo())) {
                FullimageDelegate delegate = FullimageDelegate.create("BGB_PICTUREA_" + BGB_NO.getEditTextInfo(),"RMBGBMST",PICTUREA.getEditTextInfo());
                this.getSupportDelegate().start(delegate);
            }
        }
    }

    @OnLongClick(R.id.iViewB)
    public boolean setiViewB(View view)
    {
        PictureSelector
                .create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .compress(true)
                .forResult(PictureConfig.UPDATE_FLAG);
        return true;
    }

    @OnClick(R.id.iViewB)
    public void openFullViewB(View view)
    {
        if(BGB_NO.getEditTextInfo()!=null&&!"".equals(BGB_NO.getEditTextInfo()))
        {
            if(!"".equals(PICTUREB.getEditTextInfo())) {
                FullimageDelegate delegate = FullimageDelegate.create("BGB_PICTUREB_" + BGB_NO.getEditTextInfo(),"RMBGBMST",PICTUREB.getEditTextInfo());
                this.getSupportDelegate().start(delegate);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
          switch (requestCode) {
              case PictureConfig.CHOOSE_REQUEST:
                  List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                  if(selectList!=null&&selectList.size()>0) {
                      final String pasth=selectList.get(0).getCompressPath();
                      EditImageDialog.Builder dialog = new EditImageDialog.Builder(mContext);
                      dialog.createSingleButtonDialog(new ConformListener() {
                          @Override
                          public void onConformClicked(String id, String name, Map map) {
                              Glide.with(_mActivity).load(pasth).into(iView);
                              if (BGB_NO.getEditTextInfo() != null && !"".equals(BGB_NO.getEditTextInfo())) {
                                  LiemsMethods.init(mContext).upLoadFile(pasth, "RMBGBMST", "BGB_PICTUREA_" + BGB_NO.getEditTextInfo() + ".JPEG");
                                  PICTUREA.setEditTextInfo("A"+System.currentTimeMillis());
                                  Toast.makeText(mContext, "图片上传成功！", Toast.LENGTH_SHORT).show();
                                  lineiViewB.setVisibility(View.VISIBLE);
                              } else {
                                  Toast.makeText(mContext, "请先保存曝光板内容再添加照片！", Toast.LENGTH_SHORT).show();
                              }
                          }
                      },pasth).show();
                  };
                  break;
              case PictureConfig.UPDATE_FLAG:
                  List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
                  if(selectList2!=null&&selectList2.size()>0) {
                      final String pasth=selectList2.get(0).getCompressPath();
                      EditImageDialog.Builder dialog = new EditImageDialog.Builder(mContext);
                      dialog.createSingleButtonDialog(new ConformListener() {
                          @Override
                          public void onConformClicked(String id, String name, Map map) {
                              Glide.with(_mActivity).load(pasth).into(iViewB);
                              if(BGB_NO.getEditTextInfo()!=null&&!"".equals(BGB_NO.getEditTextInfo())) {
                                  LiemsMethods.init(mContext).upLoadFile(pasth, "RMBGBMST", "BGB_PICTUREB_"+BGB_NO.getEditTextInfo()+".JPEG");
                                  PICTUREB.setEditTextInfo("A"+System.currentTimeMillis());
                                  Toast.makeText(mContext,"图片上传成功！", Toast.LENGTH_SHORT).show();
                              }else {
                                  Toast.makeText(mContext,"请先保存曝光板内容再添加照片！", Toast.LENGTH_SHORT).show();
                              }
                          }
                      },pasth).show();
                  }
                  break;
          }
        }
    }




    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return new DefaultHorizontalAnimator();
    }

    public static BgbYourBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("BGB_NO", pictures);
        final BgbYourBeanDelegate delegate = new BgbYourBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getBgbDetail")
                            .params("bgbno", pkValue)
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
                            BGB_NO.setEditTextInfo(entity.getString("BGB_NO"));
                            BF_TYP.setEditTextTagInfo(entity.getString("BF_TYP"),"RMBGBMST@@BF_TYP");
                            BG_NOT.setEditTextInfo(entity.getString("BG_NOT"));
                            CST_NO.setEditTextTagInfo(entity.getString("CST_NO"),"BgbzgcstOption");
                            BG_ADR.setEditTextTagInfo(entity.getString("BG_ADR"),"RMBGBMST@@BG_ADR");
                            JC_DTM.setEditTextInfo(entity.getString("JC_DTM"));
                            DO_DTM.setEditTextInfo(entity.getString("DO_DTM"));
                            JC_TYP.setEditTextTagInfo(entity.getString("JC_TYP"),"BgbjcnrOption");
                            KH_NUM.setEditTextInfo(entity.getString("KH_NUM"));
                            PLAN_DTM.setEditTextInfo(entity.getString("PLAN_DTM"));
                            GLCST_NO.setEditTextTagInfo(entity.getString("GLCST_NO"),"BgbcstOption");
                            SKL_NO.setEditTextTagInfo(entity.getString("SKL_NO"));
                            SKL_NO.setEditTextInfo(entity.getString("SKL_NAM"));
                            ZGSKL_NO.setEditTextTagInfo(entity.getString("ZGSKL_NO"));
                            ZGSKL_NO.setEditTextInfo(entity.getString("ZGSKL_NAM"));
                            GLUSR_ID.setEditTextTagInfo(entity.getString("GLUSR_ID"));
                            GLUSR_ID.setEditTextInfo(entity.getString("GLUSR_NAM"));
                            JCUSR_ID.setEditTextTagInfo(entity.getString("JCUSR_ID"));
                            JCUSR_ID.setEditTextInfo(entity.getString("JCUSR_NAM"));
                            PICTUREA.setEditTextInfo(entity.getString("PICTUREA"));
                            PICTUREB.setEditTextInfo(entity.getString("PICTUREB"));
                            lineiViewA.setVisibility(View.VISIBLE);
                            if(!"".equals(entity.getString("PICTUREA"))) {
                                LiemsMethods.init(mContext).glideImage(thisdelegate, iView, "RMBGBMST",
                                        "BGB_PICTUREA_" + entity.getString("BGB_NO") + ".JPEG",entity.getString("PICTUREA"));
                                lineiViewB.setVisibility(View.VISIBLE);
                                if(!"".equals(entity.getString("PICTUREB"))) {
                                    LiemsMethods.init(mContext).glideImage(thisdelegate, iViewB, "RMBGBMST",
                                            "BGB_PICTUREB_" + entity.getString("BGB_NO") + ".JPEG",entity.getString("PICTUREB"));
                                }
                            }
                        }
                    }
                }
            });
        }else {
            if(mUser!=null) {

            }
        }
    }
}
