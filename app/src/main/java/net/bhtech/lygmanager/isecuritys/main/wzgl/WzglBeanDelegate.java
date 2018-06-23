package net.bhtech.lygmanager.isecuritys.main.wzgl;

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
import net.bhtech.lygmanager.utils.log.LatteLogger;

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

public class WzglBeanDelegate extends BottomItemDelegate {

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
    @BindView(R.id.KH_TYP)
    RightAndLeftEditText KH_TYP=null;
    @BindView(R.id.BG_NOT)
    RightAndLeftEditText BG_NOT=null;
    @BindView(R.id.JC_DTM)
    RightAndLeftEditText JC_DTM=null;
    @BindView(R.id.JCST_NO)
    RightAndLeftEditText JCST_NO=null;
    @BindView(R.id.JCUSR_ID)
    RightAndLeftEditText JCUSR_ID=null;
    @BindView(R.id.DO_DTM)
    RightAndLeftEditText DO_DTM=null;
    @BindView(R.id.KH_NUM)
    RightAndLeftEditText KH_NUM=null;
    @BindView(R.id.PLAN_DTM)
    RightAndLeftEditText PLAN_DTM=null;
    @BindView(R.id.ZR_USR)
    RightAndLeftEditText ZR_USR=null;
    @BindView(R.id.KH_FS)
    RightAndLeftEditText KH_FS=null;
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
    private WzglBeanDelegate thisdelegate=this;

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
        return R.layout.delegate_wzglbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("违章明细");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        button_commit.setVisibility(View.VISIBLE);
        button_commit.setText("{fa-plus}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("RMWZGLMST@@BF_TYP,RMWZGLMST@@BG_ADR,RMWZGLMST@@KH_TYP");
        LiemsMethods.init(getContext()).getLiemsOption("getBgbzgcstOption","BgbzgcstOption");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String today=sdf.format(new Date());
        JC_DTM.setDatePick(this,today,"DATE");
        DO_DTM.setDatePick(this,today,"DATE");
        PLAN_DTM.setDatePick(this,today,"DATE");
        BF_TYP.setPopulWindow(mContext,"RMWZGLMST@@BF_TYP");
        KH_TYP.setPopulWindow(mContext,"RMWZGLMST@@KH_TYP");
        BG_ADR.setPopulWindow(mContext,"RMWZGLMST@@BG_ADR");
        CST_NO.setPopulWindow(mContext,"BgbzgcstOption");
        ZR_USR.setCstUserDialog(mContext);
//

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> entity=new HashMap<>();
                entity.put("BGB_NO",BGB_NO.getEditTextInfo());
                entity.put("CST_NO",CST_NO.getEditTextTagInfo());
                entity.put("BG_ADR",BG_ADR.getEditTextTagInfo());
                entity.put("BF_TYP",BF_TYP.getEditTextTagInfo());
                entity.put("KH_TYP",KH_TYP.getEditTextTagInfo());
                entity.put("BG_NOT",BG_NOT.getEditTextInfo());
                entity.put("JC_DTM",JC_DTM.getEditTextInfo());
                entity.put("KH_NUM",KH_NUM.getEditTextInfo());
                entity.put("PLAN_DTM",PLAN_DTM.getEditTextInfo());
                entity.put("KHBZ_DSC","依据《通辽霍林河坑口发电有限责任公司反违章管理标准》相关条款。");
                if(DO_DTM.getEditTextInfo()!=null&&!"".equals(DO_DTM.getEditTextInfo())) {
                    entity.put("DO_DTM", DO_DTM.getEditTextInfo());
                    entity.put("YSUSR_ID", mUser.getUserId());
                }
                String bfTyp=BF_TYP.getEditTextTagInfo();
                if("01".equals(bfTyp))
                    entity.put("KH_FS","1");
                else if("02".equals(bfTyp))
                    entity.put("KH_FS","3");
                else if("03".equals(bfTyp))
                    entity.put("KH_FS","2");
                else if("04".equals(bfTyp))
                    entity.put("KH_FS","5");
                else if("05".equals(bfTyp))
                    entity.put("KH_FS","10");

                entity.put("ZR_USR",ZR_USR.getEditTextTagInfo());
                entity.put("ORG_NO",mUser.getOrgNo());
                entity.put("JCUSR_ID",mUser.getUserId());
                entity.put("JCST_NO",mUser.getCstNo());
                entity.put("JLUSR_ID",mUser.getUserId());
                entity.put("JLUSR_DTM",today);
                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateWzglMST")
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
                BG_ADR.clearText();
                CST_NO.clearText();
                BF_TYP.clearText();
                KH_TYP.clearText();
                BG_NOT.clearText();
                ZR_USR.clearText();
                KH_FS.clearText();
                KH_NUM.clearText();
                PICTUREA.clearText();
                iView.setImageBitmap(null);
            }
        });
    }

    @OnLongClick(R.id.iView)
    public boolean setiView(View view)
    {
        PictureSelector
                .create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
        return true;
    }

    @OnClick(R.id.iView)
    public void openFullView(View view)
    {
        if(BGB_NO.getEditTextInfo()!=null&&!"".equals(BGB_NO.getEditTextInfo()))
        {
            if(!"".equals(PICTUREA.getEditTextInfo())) {
                FullimageDelegate delegate = FullimageDelegate.create("BGB_PICTUREA_" + BGB_NO.getEditTextInfo(),"RMWZGLMST",PICTUREA.getEditTextInfo());
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
                FullimageDelegate delegate = FullimageDelegate.create("BGB_PICTUREB_" + BGB_NO.getEditTextInfo(),"RMWZGLMST",PICTUREB.getEditTextInfo());
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
                                  LiemsMethods.init(mContext).upLoadFile(pasth, "RMWZGLMST", "BGB_PICTUREA_" + BGB_NO.getEditTextInfo() + ".JPEG");
                                  PICTUREA.setEditTextInfo("A");
                                  Toast.makeText(mContext, "图片上传成功！", Toast.LENGTH_SHORT).show();
                                  lineiViewB.setVisibility(View.VISIBLE);
                              } else {
                                  Toast.makeText(mContext, "请先保存违章内容再添加照片！", Toast.LENGTH_SHORT).show();
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
                                  LiemsMethods.init(mContext).upLoadFile(pasth, "RMWZGLMST", "BGB_PICTUREB_"+BGB_NO.getEditTextInfo()+".JPEG");
                                  PICTUREB.setEditTextInfo("A");
                                  Toast.makeText(mContext,"图片上传成功！", Toast.LENGTH_SHORT).show();
                              }else {
                                  Toast.makeText(mContext,"请先保存违章内容再添加照片！", Toast.LENGTH_SHORT).show();
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

    public static WzglBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("BGB_NO", pictures);
        final WzglBeanDelegate delegate = new WzglBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getWzglDetail")
                            .params("bgbno", pkValue)
                            .loader(this.getContext())
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
                @Override
                public void onNext(String result) {
                    LatteLogger.d(result);
                    JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                    LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                    if ("success".equals(lr.getResult()) && !"0".equals(lr.getCount())) {
                        JSONObject entity = JSONObject.parseObject(lr.getRows().toString());
                        if ( entity!=null) {
                            BGB_NO.setEditTextInfo(entity.getString("BGB_NO"));
                            BF_TYP.setEditTextTagInfo(entity.getString("BF_TYP"),"RMWZGLMST@@BF_TYP");
                            KH_TYP.setEditTextTagInfo(entity.getString("KH_TYP"),"RMWZGLMST@@KH_TYP");
                            BG_NOT.setEditTextInfo(entity.getString("BG_NOT"));
                            CST_NO.setEditTextTagInfo(entity.getString("CST_NO"),"BgbzgcstOption");
                            BG_ADR.setEditTextTagInfo(entity.getString("BG_ADR"),"RMWZGLMST@@BG_ADR");

                            JCST_NO.setEditTextTagInfo(entity.getString("JCST_NO"));
                            JCST_NO.setEditTextInfo(entity.getString("JCST_NAM"));
                            JCUSR_ID.setEditTextTagInfo(entity.getString("JCUSR_ID"));
                            JCUSR_ID.setEditTextInfo(entity.getString("JCUSR_NAM"));

                            JC_DTM.setEditTextInfo(entity.getString("JC_DTM"));
                            DO_DTM.setEditTextInfo(entity.getString("DO_DTM"));
                            KH_NUM.setEditTextInfo(entity.getString("KH_NUM"));
                            PLAN_DTM.setEditTextInfo(entity.getString("PLAN_DTM"));
                            ZR_USR.setEditTextTagInfo(entity.getString("ZR_USR"));
                            ZR_USR.setEditTextInfo(entity.getString("GLUSR_NAM"));
                            KH_FS.setEditTextInfo(entity.getString("KH_FS"));
                            PICTUREA.setEditTextInfo(entity.getString("PICTUREA"));
                            PICTUREB.setEditTextInfo(entity.getString("PICTUREB"));
                            lineiViewA.setVisibility(View.VISIBLE);
                            if(!"".equals(entity.getString("PICTUREA"))) {
                                LiemsMethods.init(mContext).glideImage(thisdelegate, iView, "RMWZGLMST",
                                        "BGB_PICTUREA_" + entity.getString("BGB_NO") + ".JPEG",entity.getString("PICTUREA"));
                                lineiViewB.setVisibility(View.VISIBLE);
                                if(!"".equals(entity.getString("PICTUREB"))) {
                                    LiemsMethods.init(mContext).glideImage(thisdelegate, iViewB, "RMWZGLMST",
                                            "BGB_PICTUREB_" + entity.getString("BGB_NO") + ".JPEG",entity.getString("PICTUREB"));
                                }
                            }
                        }
                    }
                }
            });
        }else {
            if(mUser!=null) {

                JCUSR_ID.setEditTextTagInfo(mUser.getUserId());
                JCUSR_ID.setEditTextInfo(mUser.getUsrNam());
                JCST_NO.setEditTextTagInfo(mUser.getCstNo());
                JCST_NO.setEditTextInfo(mUser.getCstName());
            }
        }
    }
}
