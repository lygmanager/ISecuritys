package net.bhtech.lygmanager.net.cxfweservice;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by zhangxinbiao on 2017/11/16.
 */

public interface CxfRestService {

    @POST("/Liems/ws/SSInterface")
    Observable<String> post(@HeaderMap Map<String,String> headerMap, @Body String body);
}
