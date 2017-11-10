package net.bhtech.lygmanager.isecuritys.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener{

    @BindView(R.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            //getParentDelegate().getSupportDelegate().start(new SearchDelegate());
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
//        CallbackManager.getInstance()
//                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
//                    @Override
//                    public void executeCallback(@Nullable String args) {
//                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();
//                    }
//                });
//        mSearchView.setOnFocusChangeListener(this);
    }
}
