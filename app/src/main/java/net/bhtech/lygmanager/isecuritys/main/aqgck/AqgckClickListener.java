package net.bhtech.lygmanager.isecuritys.main.aqgck;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.isecuritys.main.equip.EquipDelegate;
import net.bhtech.lygmanager.isecuritys.main.task.TaskDelegate;
import net.bhtech.lygmanager.isecuritys.main.tube.OutnumDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

/**
 * Created by zhangxinbiao
 */

public class AqgckClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private AqgckClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new AqgckClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("AQ_NO");
        if(id!=null&&!"".equals(id)) {
            AqgckBeanDelegate aqgckBeanDelegate = AqgckBeanDelegate.create(id);
            DELEGATE.getSupportDelegate().start(aqgckBeanDelegate);
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
