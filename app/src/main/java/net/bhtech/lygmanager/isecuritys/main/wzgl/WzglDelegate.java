package net.bhtech.lygmanager.isecuritys.main.wzgl;

import android.graphics.Color;

import net.bhtech.lygmanager.delegates.bottom.BaseBottomDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomTabBean;
import net.bhtech.lygmanager.delegates.bottom.ItemBuilder;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.bgb.BgbMyDelegate;
import net.bhtech.lygmanager.isecuritys.main.bgb.BgbYourDelegate;

import java.util.LinkedHashMap;

/**
 * Created by zhangxinbiao on 2017/11/26.
 */

public class WzglDelegate extends BaseBottomDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_mount;
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-list-alt}", "我的发现"), new WzglMyDelegate());
        items.put(new BottomTabBean("{fa-list}", "我的整改项"), new WzglYourDelegate());
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
