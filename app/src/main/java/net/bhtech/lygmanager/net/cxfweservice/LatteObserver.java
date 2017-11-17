package net.bhtech.lygmanager.net.cxfweservice;

import android.content.Context;
import android.widget.Toast;

import net.bhtech.lygmanager.ui.loader.LatteLoader;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhangxinbiao on 2017/11/17.
 */

public abstract class LatteObserver<T> implements Observer<T> {
    private Context mContext=null;

    public LatteObserver(Context context){
        mContext=context;
    }
    @Override
    public void onSubscribe(Disposable d) {
        LatteLogger.d(d);
    }

    @Override
    public abstract void onNext(T o) ;

    @Override
    public void onError(Throwable e) {
        LatteLogger.d("a",e.getMessage());
        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
        LatteLoader.stopLoading();
    }

    @Override
    public void onComplete() {
        LatteLoader.stopLoading();
    }
}
