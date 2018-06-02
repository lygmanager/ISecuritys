package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

/**
 * Created by zhangxinbiao
 */

public class LxzbkClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private LxzbkClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new LxzbkClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("JCK_NO");
        if(id!=null&&!"".equals(id)) {
            LxzbkBeanDelegate delegate = LxzbkBeanDelegate.create(id);
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
