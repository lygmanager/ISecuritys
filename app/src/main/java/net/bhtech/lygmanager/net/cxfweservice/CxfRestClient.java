package net.bhtech.lygmanager.net.cxfweservice;

import android.content.Context;

import net.bhtech.lygmanager.net.callback.IRequest;
import net.bhtech.lygmanager.net.cxfweservice.ksoap2.transport.SoapHelper;
import net.bhtech.lygmanager.ui.loader.LatteLoader;
import net.bhtech.lygmanager.ui.loader.LoaderStyle;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public final class CxfRestClient<T> {
    private static final HashMap<String, Object> PARAMS = CxfRestCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    private Map<String,String> soapHeaderMap;
    private String mBody;

    CxfRestClient(String url,
               Map<String, Object> params,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               RequestBody body,
               File file,
               Context context,
               LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static CxfRestClientBuilder builder() {
        return new CxfRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final CxfRestService service = CxfRestCreator.getRestService();
        Observable<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        String nameSpace="http://server.luculent.net/";
        List<Object> getParamters =  SoapHelper.getInstance().getParams(this.URL,nameSpace,this.PARAMS);
        if(getParamters!=null){
            soapHeaderMap = (HashMap<String, String>) getParamters.get(0);
            mBody = new String((byte[]) getParamters.get(1));
        }

        call=service.post(soapHeaderMap, mBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return call;
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final  Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

}
