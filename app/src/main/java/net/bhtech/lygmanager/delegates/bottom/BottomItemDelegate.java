package net.bhtech.lygmanager.delegates.bottom;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.bhtech.lygmanager.delegates.LatteDelegate;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;

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

    public boolean checkIsEmpty(Context mContext,LinearLayout ll_equip)
    {
        for(int i=0;i<ll_equip.getChildCount();i++)
        {
            View viewchild2 = ll_equip.getChildAt(i);
            if(viewchild2 instanceof RightAndLeftEditText) {
                RightAndLeftEditText viewchild=(RightAndLeftEditText)viewchild2;
                if (!viewchild.isCanEmpty() && "".equals(viewchild.getEditTextInfo())) {
                    Toast.makeText(mContext, viewchild.getTextView().getText() + "不能为空！", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }
}
