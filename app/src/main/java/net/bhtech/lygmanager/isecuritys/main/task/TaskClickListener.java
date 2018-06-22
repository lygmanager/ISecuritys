package net.bhtech.lygmanager.isecuritys.main.task;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.hsetools.task.SmusrDelegate;
import net.bhtech.lygmanager.isecuritys.hsetools.tgl.TglBeanDelegate;
import net.bhtech.lygmanager.isecuritys.hsetools.tgl.TglDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.aqgck.AqgckDelegate;
import net.bhtech.lygmanager.isecuritys.main.bgb.BgbBaseDelegate;
import net.bhtech.lygmanager.isecuritys.main.bgb.BgbBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.equip.WorksheetDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.lxzbk.LxzbkDelegate;
import net.bhtech.lygmanager.isecuritys.main.wzgl.WzglBeanDelegate;
import net.bhtech.lygmanager.isecuritys.main.wzgl.WzglDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;

/**
 * Created by zhangxinbiao
 */

public class TaskClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private TaskClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new TaskClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField(MultipleFields.ID);
        switch (id){
            case "1001":
                DELEGATE.getSupportDelegate().start(new SmusrDelegate());
                break;
            default:break;
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
