package net.bhtech.lygmanager.ui.recycler;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import net.bhtech.lygmanager.ui.tag.RightAndLeftTextView;

/**
 * Created by zhangxb
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }

    public BaseViewHolder setRightText(@IdRes int viewId, String value) {
        RightAndLeftTextView view = getView(viewId);
        if(view!=null)
            view.setRightText(value);
        return this;
    }
}
