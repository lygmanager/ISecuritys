package net.bhtech.lygmanager.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.database.MountEntity;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.isecuritys.main.index.IndexDataEntity;
import net.bhtech.lygmanager.net.RestClient;
import net.bhtech.lygmanager.net.cxfweservice.CxfRestClient;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.net.rx.RxRestClientBuilder;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.ui.recycler.MultipleRecyclerAdapter;
import net.bhtech.lygmanager.utils.log.LatteLogger;


import java.util.List;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private final DataConverter CONVERTER;
    private MultipleRecyclerAdapter mAdapter = null;

    private UtusrEntity mUser=AccountManager.getSignInfo();

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,
                           RecyclerView recyclerView,
                           DataConverter converter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView, DataConverter converter) {
        return new RefreshHandler(swipeRefreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 1000);
    }

    public void firstPage() {

        mAdapter = MultipleRecyclerAdapter.create(CONVERTER);
        RECYCLERVIEW.setAdapter(mAdapter);

    }

    public void firstPage(Context _mActivity) {

        Observable<String> obj =
                RxRestClient.builder()
                        .url("getTasknumList")
                        .params("orgno", mUser.getOrgNo())
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(result));
                    RECYCLERVIEW.setAdapter(mAdapter);
                }
            }
        });

    }

    public void getDefectList(String elcId, Context _mActivity) {
//        Observable<String> obj= CxfRestClient.builder()
//                .url("getLimlistByElcId")
//                .params("arg0", "3")
//                .params("arg1", elcId)
//                .build()
//                .post();
//        obj.subscribe(new LatteObserver<String>(_mActivity) {
//            @Override
//            public void onNext(String o) {
//                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
//                RECYCLERVIEW.setAdapter(mAdapter);
//            }
//        });

        Observable<String> obj=
                RxRestClient.builder()
                        .url("getLimlistByElcId")
                        .params("arg0", "3")
                        .params("arg1", elcId)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getAqgcktList( Context _mActivity) {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("getAQGCKList")
                        .params("orgno", mUser.getOrgNo())
                        .params("userid", mUser.getUserId())
                        .params("page", "1")
                        .params("limit", "20")
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getTglList( Context _mActivity) {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("getTglList")
                        .params("orgno", mUser.getOrgNo())
                        .params("usrid", mUser.getUserId())
                        .params("page", "1")
                        .params("limit", "20")
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getCommonList( Context _mActivity,String mUrl,WeakHashMap<String, Object> params) {
        String wherelimit="";
        if(params!=null&&!params.isEmpty())
        {
            wherelimit=JSONObject.toJSONString(params);
        }
        Observable<String> obj=
                RxRestClient.builder()
                        .url(mUrl)
                        .params("wherelimit",wherelimit)
                        .params("orgno", mUser.getOrgNo())
                        .params("page", "1")
                        .params("limit", "20")
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getBgbList( Context _mActivity,String mType) {
        String params="";
        if("MY".equals(mType)){
            params=" and JLUSR_ID='"+mUser.getUserId()+"' and DO_NOT<>'02' ";
        }else if("YOUR".equals(mType)){
            params=" and GLUSR_ID='"+mUser.getUserId()+"' and DO_NOT='01' and PICTUREB is null ";
        }
        Observable<String> obj= RxRestClient.builder()
                        .url("getBgbList")
                        .params("orgno", mUser.getOrgNo())
                        .params("params",params)
                        .params("page", "1")
                        .params("limit", "10")
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getWzglList( Context _mActivity,String mType) {
        String params="";
        if("MY".equals(mType)){
            params=" and JLUSR_ID='"+mUser.getUserId()+"' and DO_NOT<>'02' ";
        }else if("YOUR".equals(mType)){
            params=" and ZR_USR='"+mUser.getUserId()+"' and DO_NOT='01' AND PICTUREB is null ";
        }
        Observable<String> obj= RxRestClient.builder()
                .url("getWzglList")
                .params("orgno", mUser.getOrgNo())
                .params("params",params)
                .params("page", "1")
                .params("limit", "10")
                .loader(_mActivity)
                .build()
                .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }

        });
    }

    public void getLxzbkList( Context _mActivity ,String ttkNo) {
        WeakHashMap<String,Object> params=new WeakHashMap<>();
        if(ttkNo!=null&&!"".equals(ttkNo))
        {
            params.put("ttkNo",ttkNo);
        }else{
            params.put("userid",mUser.getUserId());
        }
        Observable<String> obj=
                RxRestClient.builder()
                        .url("getLxzbkList")
                        .params("orgno", mUser.getOrgNo())
                        .params(params)
                        .params("page", "1")
                        .params("limit", "20")
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getVdvenList( Context _mActivity ,String vendornam) {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("getVdvenList")
                        .params("orgno", mUser.getOrgNo())
                        .params("vendornam", vendornam)
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getLxzbklinList( Context _mActivity,String jckno) {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("getLxzbklinList")
                        .params("jckno", jckno)
                        .loader(_mActivity)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }
    /*
     *工作票清单
     */
    public void getWorksheetList( Context _mActivity) {
        Observable<String> obj= RxRestClient.builder()
                .url("getRmttkList")
                .params("orgno", mUser.getOrgNo())
                .params("usrid", mUser.getUserId())
                .params("page", 1)
                .params("limit", 20)
                .loader(_mActivity)
                .build()
                .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    /*
     *超龄人员查询
     */
    public void getSmusrList( Context _mActivity) {
        Observable<String> obj= RxRestClient.builder()
                .url("getSmusrList")
                .params("orgno", mUser.getOrgNo())
                .params("page", 1)
                .params("limit", 50)
                .loader(_mActivity)
                .build()
                .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    public void getMountList(List<MountEntity> ls) {
        String o="[]";
        o= JSONArray.toJSONString(ls,true);
        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
        RECYCLERVIEW.setAdapter(mAdapter);
    }

    public void getMountList(String elcId, Context _mActivity) {
        Observable<String> obj= CxfRestClient.builder()
                .url("getLimlistByElcId")
                .params("arg0", "3")
                .params("arg1", elcId)
                .build()
                .post();
        obj.subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {
                mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(o));
                RECYCLERVIEW.setAdapter(mAdapter);
            }
        });
    }

    private void paging(final String url) {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();

    }

    @Override
    public void onRefresh() {
        refresh();
    }


    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}

