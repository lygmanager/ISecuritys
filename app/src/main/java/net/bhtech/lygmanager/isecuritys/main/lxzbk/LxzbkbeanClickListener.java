package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

/**
 * Created by zhangxinbiao
 */

public class LxzbkbeanClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private LxzbkbeanClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new LxzbkbeanClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("JCLIN_NO");
        if(id!=null&&!"".equals(id)) {
            LxzbklinBeanDelegate delegate = LxzbklinBeanDelegate.create(id);
            DELEGATE.getSupportDelegate().start(delegate);
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
