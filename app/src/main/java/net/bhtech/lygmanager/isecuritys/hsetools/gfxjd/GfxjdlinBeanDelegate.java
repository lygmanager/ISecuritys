package net.bhtech.lygmanager.isecuritys.hsetools.gfxjd;

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
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkbeanClickListener;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;

import java.text.SimpleDateFormat;
import java.util.WeakHashMap;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class GfxjdlinBeanDelegate extends BottomItemDelegate {

    private String pkValue="";

    @BindView(R.id.button_commit)
    IconTextView button_commit=null;
    @BindView(R.id.button_forward)
    IconTextView button_forward=null;
    @BindView(R.id.text_title)
    TextView text_title=null;

    @BindView(R.id.JDLIN_NO)
    RightAndLeftEditText JDLIN_NO=null;
    @BindView(R.id.JDLIN_JL)
    RightAndLeftEditText JDLIN_JL=null;
    @BindView(R.id.JDLIN_DESC)
    RightAndLeftEditText JDLIN_DESC=null;

    private GfxjdlinBeanDelegate thisdelegate=this;

    protected Context mContext=null;

    private UtusrEntity mUser=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            pkValue=args.getString("JDLIN_NO");
        }
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_gfxjdlinbean;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("高风险现场监督项目");
        button_forward.setVisibility(View.VISIBLE);
        button_forward.setText("{fa-save}");
        mContext=this.getContext();
        mUser=AccountManager.getSignInfo();
        thisdelegate=this;
        LiemsMethods.init(mContext).getFieldOption("SFGFXJDLIN@@JDLIN_JL");

        JDLIN_JL.setPopulWindow(mContext,"SFGFXJDLIN@@JDLIN_JL");

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeakHashMap<String,Object> params=new WeakHashMap<>();
                params.put("JDLIN_NO",JDLIN_NO.getEditTextInfo());
                params.put("JDLIN_JL",JDLIN_JL.getEditTextTagInfo());

                Observable<String> obj=
                        RxRestClient.builder()
                                .url("saveOrUpdateGfxjdlinMST")
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
                                    JDLIN_NO.getEditText().setText(rst.getPkValue());
                                    Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                                    thisdelegate.getFragmentManager().popBackStack();
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

    public static GfxjdlinBeanDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("JDLIN_NO", pictures);
        final GfxjdlinBeanDelegate delegate = new GfxjdlinBeanDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onLazyInitView(Bundle bundle) {
        if (pkValue != null && !"".equals(pkValue) && !"-1".equals(pkValue)) {
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getGfxjdlinList")
                            .params("wherelimit", "{\"JDLIN_NO\":\""+pkValue+"\"}")
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
                                JDLIN_NO.setEditTextInfo(entity.getString("JDLIN_NO"));
                                JDLIN_DESC.setEditTextInfo(entity.getString("JDLIN_DESC"));
                                JDLIN_JL.setEditTextTagInfo(entity.getString("JDLIN_JL"),"SFGFXJDLIN@@JDLIN_JL");
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
