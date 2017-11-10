package net.bhtech.lygmanager.delegates;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public abstract class LatteDelegate extends BaseDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}