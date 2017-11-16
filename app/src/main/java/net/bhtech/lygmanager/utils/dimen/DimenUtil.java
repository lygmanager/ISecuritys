package net.bhtech.lygmanager.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import net.bhtech.lygmanager.app.Latte;


/**
 * Created by zhangxinbiao on 2017/11/16
 */

public final class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
