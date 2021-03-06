package net.bhtech.lygmanager.isecuritys.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.isecuritys.main.equip.EquipDelegate;
import net.bhtech.lygmanager.isecuritys.main.task.TaskDelegate;
import net.bhtech.lygmanager.isecuritys.main.tube.OutnumDelegate;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

/**
 * Created by zhangxinbiao
 */

public class OverrunItemClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private OverrunItemClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate) {
        return new OverrunItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final MultipleItemEntity entity = (MultipleItemEntity) baseQuickAdapter.getData().get(position);
        final String id = entity.getField(MultipleFields.ID);
        switch (id){
            case "1":
                DELEGATE.getSupportDelegate().start(new OutnumDelegate());
                break;
            case "2":
                DELEGATE.getSupportDelegate().start(new EquipDelegate());
                break;
            case "3":
                DELEGATE.getSupportDelegate().start(new TaskDelegate());
                break;
            case "4":
                DELEGATE.getSupportDelegate().start(new TaskDelegate());
                break;
            case "5":
                DELEGATE.getSupportDelegate().start(new TaskDelegate());
                break;
            case "6":
                DELEGATE.getSupportDelegate().start(new TaskDelegate());
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
