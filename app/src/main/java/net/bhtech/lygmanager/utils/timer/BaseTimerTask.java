package net.bhtech.lygmanager.utils.timer;

import java.util.TimerTask;

/**
 * Created by zhangxinbiao on 2017/11/12.
 */

public class BaseTimerTask extends TimerTask{
    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
