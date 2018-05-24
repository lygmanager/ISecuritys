package net.bhtech.lygmanager.net.rx;

import android.content.Context;

import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.net.RestClient;
import net.bhtech.lygmanager.net.RestCreator;
import net.bhtech.lygmanager.net.callback.IError;
import net.bhtech.lygmanager.net.callback.IFailure;
import net.bhtech.lygmanager.net.callback.IRequest;
import net.bhtech.lygmanager.net.callback.ISuccess;
import net.bhtech.lygmanager.ui.loader.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 傅令杰 on 2017/4/2
 */

public final class RxRestClientBuilder {

    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private String mUrl = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    private String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        if(url.startsWith("http")) {
            this.mUrl = url;
        }else {
            this.mUrl = BASE_URL+url;
        }
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        return new RxRestClient(mUrl, PARAMS,mBody, mFile, mContext,mLoaderStyle);
    }
}
