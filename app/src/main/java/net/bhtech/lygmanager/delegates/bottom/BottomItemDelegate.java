package net.bhtech.lygmanager.delegates.bottom;

import net.bhtech.lygmanager.delegates.LatteDelegate;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public abstract class BottomItemDelegate extends LatteDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 500L;
    private long TOUCH_TIME = 0;

//    @Override
//    public boolean onBackPressedSupport() {
//        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
//            _mActivity.finish();
//        } else {
//            TOUCH_TIME = System.currentTimeMillis();
//            Toast.makeText(_mActivity, "双击退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

}
