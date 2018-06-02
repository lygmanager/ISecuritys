package net.bhtech.lygmanager.isecuritys.main.wzgl;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.main.bgb.BgbBeanDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

/**
 * Created by zhangxinbiao
 */

public class WzglClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;
    private String MType="";

    private WzglClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }
    private WzglClickListener(LatteDelegate delegate,String mType) {
        this.DELEGATE = delegate;
        this.MType=mType;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new WzglClickListener(delegate);
    }

    public static SimpleClickListener create(LatteDelegate delegate,String mType) {
        return new WzglClickListener(delegate,mType);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField("BGB_NO");
        if(id!=null&&!"".equals(id)) {
            if("".equals(MType)) {
                WzglBeanDelegate delegate = WzglBeanDelegate.create(id);
                DELEGATE.getParentDelegate().getSupportDelegate().start(delegate);
            }else{
                MyWzglBeanDelegate delegate = MyWzglBeanDelegate.create(id);
                DELEGATE.getParentDelegate().getSupportDelegate().start(delegate);
            }
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
