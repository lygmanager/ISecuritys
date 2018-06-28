package net.bhtech.lygmanager.isecuritys.main.equip;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbklinBeanDelegate;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.ui.tag.CompoundButtonGroup;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao
 */

public class WorsheetClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;
    private final Context mContext;
    private UtusrEntity mUser;

    private WorsheetClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
        this.mContext=delegate.getContext();
        mUser = AccountManager.getSignInfo();
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new WorsheetClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("TTK_NO");
        if(id!=null&&!"".equals(id)) {

            LxzbkDelegate delegate = LxzbkDelegate.create(id);
            DELEGATE.getSupportDelegate().start(delegate);

        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("TTK_NO");
        final String adr = entity.getField("TTK_ADR");
        final String dcs = entity.getField("ADR_DSC");
        if(id!=null&&!"".equals(id)) {
            WeakHashMap<String,Object> params=new WeakHashMap<>();
            params.put("orgno", mUser.getOrgNo());
            CompoundButtonGroup.CompoundButtonAlertDialog(DELEGATE.getContext(), "getLxzbklxList", params, new CompoundButtonGroup.ConformListener() {
                @Override
                public void onConformClicked(String values,String texts) {
                    if(!"".equals(values))
                    createLxzbk(id,adr,values,dcs);

                }
            });
        }

    }

    public void createLxzbk(String ttkNo,String adr,String lx,String dcs)
    {
        WeakHashMap<String,Object> params=new WeakHashMap<>();

        params.put("JCK_NO","");
        params.put("ORG_NO",mUser.getOrgNo());
        params.put("JCKUSR_ID",mUser.getUserId());
        params.put("JCKCST_NO",mUser.getPlaNo());
        params.put("CRW_NO",mUser.getCrwNo());
        params.put("JCK_TYP",lx);
        params.put("TTK_NO",ttkNo);
        params.put("JCK_DSC",adr);
        params.put("JCK_ADR",dcs);
        Observable<String> obj =
                RxRestClient.builder()
                        .url("saveOrUpdateLXZBKMST")
                        .params("totaljson", JSONObject.toJSONString(params))
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    Toast.makeText(mContext,result,Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

}
