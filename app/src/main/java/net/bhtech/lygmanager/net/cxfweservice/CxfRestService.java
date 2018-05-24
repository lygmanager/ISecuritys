package net.bhtech.lygmanager.net.cxfweservice;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import java.util.WeakHashMap;

/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public interface CxfRestService {

    @POST("/Liems/ws/SSInterface?wsdl")
    Observable<String> post(@HeaderMap Map<String,String> headerMap, @Body String body);

//    @FormUrlEncoded
//    @POST
//    Observable<String> post(@Url String url, @FieldMap HashMap<String, Object> params);

}
