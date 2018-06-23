package net.bhtech.lygmanager.isecuritys.hsetools.fgl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
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

public class FglBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;
    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.FGL_NO)
    RightAndLeftEditText FGL_NO=null;
    @BindView(R.id.TGL_ID)
    RightAndLeftEditText TGL_ID=null;
    @BindView(R.id.CBSPRJ_MAN)
    RightAndLeftEditText CBSPRJ_MAN=null;
    @BindView(R.id.TG_CBS)
    RightAndLeftEditText TG_CBS=null;
    @BindView(R.id.PRJ_NO)
    RightAndLeftEditText PRJ_NO=null;
    @BindView(R.id.TG_QY)
    RightAndLeftEditText TG_QY=null;
    @BindView(R.id.JZ_SHT)
    RightAndLeftEditText JZ_SHT=null;
    @BindView(R.id.IS_JZ)
    RightAndLeftEditText IS_JZ=null;
    @BindView(R.id.CBSHSE_DTM)
    RightAndLeftEditText CBSHSE_DTM=null;
    @BindView(R.id.VALID_STA)
    RightAndLeftEditText VALID_STA=null;

    private FglBeanDelegate thisdelegate=this;


    protected Context mContext=null;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("pkValue");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_fglbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("复工令明细");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        CBSHSE_DTM.setDatePick(this,sdf.format(new Date()),"DATE");
        IS_JZ.setPopulWindow(mContext,"HSEFGLMST@@IS_JZ");
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"00".equals(VALID_STA.getEditTextTagInfo()))
                {
                    Toast.makeText(mContext, "当前状态下不允许修改！", Toast.LENGTH_LONG).show();
                    return;
                }
                WeakHashMap<String,Object> params=new WeakHashMap<>();
                params.put("FGL_NO",FGL_NO.getEditTextInfo());
                params.put("PRJ_NO",PRJ_NO.getEditTextInfo());
                params.put("CBSHSE_DTM",CBSHSE_DTM.getEditTextInfo());
                params.put("LSTUSR_ID",mUser.getUserId());
                params.put("IS_JZ",IS_JZ.getEditTextTagInfo());
                params.put("JZ_SHT",JZ_SHT.getEditTextInfo());

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
                                if(rst.getPkValue()!=null&&!"".equals(rst.getPkValue()))
                                {
                                    FGL_NO.getEditText().setText(rst.getPkValue());
                                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(mContext, rst.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator()
    {
        return new DefaultHorizontalAnimator();
    }

    public static FglBeanDelegate create(String pkValue) {
        final Bundle args = new Bundle();
        args.putString("pkValue", pkValue);
        final FglBeanDelegate delegate = new FglBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getFglDetail")
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
                            FGL_NO.setEditTextInfo(entity.getString("FGL_NO"));
                            TGL_ID.setEditTextInfo(entity.getString("TGL_ID"));
                            TG_CBS.setEditTextTagInfo(entity.getString("TG_CBS"));
                            TG_CBS.setEditTextInfo(entity.getString("TG_CBS_NAM"));
                            CBSPRJ_MAN.setEditTextInfo(entity.getString("CBSPRJ_MAN"));
                            CBSPRJ_MAN.setEditTextInfo(entity.getString("CBSPRJ_MAN_NAM"));
                            TG_QY.setEditTextInfo(entity.getString("TG_QY"));
                            PRJ_NO.setEditTextInfo(entity.getString("PRJ_NO"));
                            JZ_SHT.setEditTextInfo(entity.getString("JZ_SHT"));
                            IS_JZ.setEditTextTagInfo(entity.getString("IS_JZ"),"HSEFGLMST@@IS_JZ");
                            VALID_STA.setEditTextTagInfo(entity.getString("VALID_STA"),"HSEFGLMST@@VALID_STA");
                            CBSHSE_DTM.setEditTextInfo(entity.getString("CBSHSE_DTM"));
                        }
                    }
                }
            });
        }
    }
}
