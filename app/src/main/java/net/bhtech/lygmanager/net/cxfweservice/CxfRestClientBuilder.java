package net.bhtech.lygmanager.net.cxfweservice;

import android.content.Context;

import net.bhtech.lygmanager.net.callback.IError;
import net.bhtech.lygmanager.net.callback.IFailure;
import net.bhtech.lygmanager.net.callback.IRequest;
import net.bhtech.lygmanager.net.callback.ISuccess;
import net.bhtech.lygmanager.ui.loader.LoaderStyle;

import java.io.File;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public class CxfRestClientBuilder {

    private static final HashMap<String, Object> PARAMS = CxfRestCreator.getParams();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    CxfRestClientBuilder() {
    }

    public final CxfRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final CxfRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final CxfRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final CxfRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final CxfRestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final CxfRestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final CxfRestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final CxfRestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final CxfRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final CxfRestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final CxfRestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final CxfRestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final CxfRestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final CxfRestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final CxfRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final CxfRestClient build() {
        return new CxfRestClient(mUrl, PARAMS,
                mDownloadDir, mExtension, mName,
                mIRequest, mBody, mFile, mContext,
                mLoaderStyle);
    }
}
