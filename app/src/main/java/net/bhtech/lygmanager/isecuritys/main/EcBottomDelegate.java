package net.bhtech.lygmanager.isecuritys.main;

import android.graphics.Color;

import net.bhtech.lygmanager.delegates.bottom.BaseBottomDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomTabBean;
import net.bhtech.lygmanager.delegates.bottom.ItemBuilder;
import net.bhtech.lygmanager.isecuritys.main.index.IndexDelegate;
import net.bhtech.lygmanager.isecuritys.main.setting.SettingDelegate;
import net.bhtech.lygmanager.isecuritys.main.task.TaskDelegate;
import net.bhtech.lygmanager.isecuritys.overrun.OverrunDelegate;

import java.util.LinkedHashMap;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class EcBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "超限预警"), new OverrunDelegate());
        items.put(new BottomTabBean("{fa-compass}", "基础信息"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "状态检测"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "统计报表"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "综合管理"), new SettingDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#0099FF");
    }
}