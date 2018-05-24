package net.bhtech.lygmanager.isecuritys.main.mount;

import android.graphics.Color;

import net.bhtech.lygmanager.delegates.bottom.BaseBottomDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomTabBean;
import net.bhtech.lygmanager.delegates.bottom.ItemBuilder;
import net.bhtech.lygmanager.isecuritys.R;

import java.util.LinkedHashMap;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class MountDelegate extends BaseBottomDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_mount;
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "在线"), new MountonDelegate());
        items.put(new BottomTabBean("{fa-sort}", "离线"), new MountoffDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#0099FF");
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }
}
