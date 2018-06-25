package net.bhtech.lygmanager.isecuritys.main.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.delegates.BaseDelegate;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.main.EcBottomDelegate;
import net.bhtech.lygmanager.net.appupdate.AppVersionEntity;
import net.bhtech.lygmanager.net.appupdate.UpdateManager;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class IndexDelegate extends BottomItemDelegate{



    @BindView(R.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R.id.rv_index)
    RecyclerView mRecyclerView = null;

    private RefreshHandler mRefreshHandler = null;
    private Context mContext=null;

    private BaseDelegate delegate=null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mContext=getContext();
        delegate=this.getParentDelegate();
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter(getContext()));
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage(mContext);
        appUpdate();
    }

    public void appUpdate() {
        String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        String url=BASE_URL.replaceAll("webservice/","apkfile/update.json");
        Observable<String> obj =
                RxRestClient.builder()
                        .url(url)
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    AppVersionEntity entity= JSONObject.parseObject(result, AppVersionEntity.class);
                    UpdateManager manager=new UpdateManager(delegate,getContext());
                    manager.checkUpdate(entity,"");
                }
            }
        });
    }
}
