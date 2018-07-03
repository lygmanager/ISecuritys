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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.LxzbkEntity;
import net.bhtech.lygmanager.database.LxzbklinEntity;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckClickListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LxzbklinBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.JCLIN_NO)
    RightAndLeftEditText JCLIN_NO=null;
    @BindView(R.id.JCLIN_DSC)
    RightAndLeftEditText JCLIN_DSC=null;
    @BindView(R.id.JCLIN_SFNUM)
    RightAndLeftEditText JCLIN_SFNUM=null;
    @BindView(R.id.JCLIN_FXNUM)
    RightAndLeftEditText JCLIN_FXNUM=null;
    @BindView(R.id.JCLIN_ZDNUM)
    RightAndLeftEditText JCLIN_ZDNUM=null;
    @BindView(R.id.VALID_STA)
    RightAndLeftEditText VALID_STA=null;

    private UtusrEntity mUser=null;
    private BottomItemDelegate thisDelegate=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("JCLIN_NO");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_lxzbklinbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("检查内容");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        thisDelegate=this;
        final Context mContext=this.getContext();
        mUser= AccountManager.getSignInfo();
        JCLIN_DSC.setReadOnly();

        Map<String,String[]> fieldOptions= LiemsMethods.init(getContext())
                .getFieldOption("SDZBJCKLIN@@VALID_STA");
        VALID_STA.setPopulWindow(mContext,"SDZBJCKLIN@@VALID_STA");
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> entity=new HashMap<>();
                entity.put("JCLIN_NO",JCLIN_NO.getEditTextInfo());
                entity.put("JCLIN_DSC",JCLIN_DSC.getEditTextInfo());
                entity.put("JCLIN_SFNUM",JCLIN_SFNUM.getEditTextInfo());
                entity.put("JCLIN_FXNUM",JCLIN_FXNUM.getEditTextInfo());
                entity.put("JCLIN_ZDNUM",JCLIN_ZDNUM.getEditTextInfo());
                entity.put("VALID_STA",VALID_STA.getEditTextTagInfo());
                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateLXZBKLINMST")
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
                                JCLIN_NO.getEditText().setText(rst.getPkValue());
                                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                                thisDelegate.getFragmentManager().popBackStack();
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {LatteLogger.d(pkValue);
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getLXZBKLINDetail")
                            .params("jclinno", pkValue)
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
                        if ( entity !=null) {
                            JCLIN_NO.setEditTextInfo(entity.getString("JCLIN_NO"));
                            JCLIN_DSC.setEditTextInfo(entity.getString("JCLIN_DSC"));
                            JCLIN_SFNUM.setEditTextInfo(entity.getString("JCLIN_SFNUM"));
                            JCLIN_FXNUM.setEditTextInfo(entity.getString("JCLIN_FXNUM"));
                            JCLIN_ZDNUM.setEditTextInfo(entity.getString("JCLIN_ZDNUM"));
                            VALID_STA.setEditTextTagInfo(entity.getString("VALID_STA"),"SDZBJCKLIN@@VALID_STA");
                        }
                    }
                }
            });
        }
    }

    public static LxzbklinBeanDelegate create(String pkval) {
        final Bundle args = new Bundle();
        args.putString("JCLIN_NO", pkval);
        final LxzbklinBeanDelegate delegate = new LxzbklinBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }
}
