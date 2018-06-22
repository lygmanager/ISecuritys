package net.bhtech.lygmanager.isecuritys.dialog.vdven;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.dialog.ConformListener;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxinbiao
 */

public class VdvenClickListener extends SimpleClickListener {

    private final ConformListener listener;
    private final VdvenDialog vdvenDialog;

    private VdvenClickListener(ConformListener listener,VdvenDialog vdvenDialog) {
        this.listener = listener;
        this.vdvenDialog = vdvenDialog;
    }

    public static SimpleClickListener create(ConformListener listener,VdvenDialog vdvenDialog) {
        return new VdvenClickListener(listener,vdvenDialog);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("VEN_NO");
        if(id!=null&&!"".equals(id)) {
            Map<String,String> params=new HashMap<>();
            params.put("MANAGER_USR",(String)entity.getField("MANAGER_USR"));
            params.put("USR_NAM",(String)entity.getField("USR_NAM"));
            listener.onConformClicked(id,(String)entity.getField("VENDOR_NAM"),params);
            vdvenDialog.hide();
        }

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

}
