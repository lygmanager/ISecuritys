package net.bhtech.lygmanager.net;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.app.ConfigKeys;
import net.bhtech.lygmanager.app.Latte;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.delegates.BaseDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.common.GlideApp;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LiemsMethods {
    private UtusrEntity mUser= null;
    private Context mContext=null;
    public static Map<String,String[]> resultMap=new HashMap<>();

    public static LiemsMethods init(Context context){
        LiemsMethods methods=new LiemsMethods();
        methods.mUser=AccountManager.getSignInfo();
        methods.mContext=context;
        return methods;
    }

    public Map<String,String[]> getFieldOption(final String mTblfields)
    {

        String[][] result=null;
        Observable<String> obj =
                RxRestClient.builder()
                        .url("getFieldOption")
                        .params("orgno", mUser.getOrgNo())
                        .params("userid", mUser.getUserId())
                        .params("tblfields",mTblfields)
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                    String[] mFieldArray=mTblfields.split(",");
                    for(int i=0;i<mFieldArray.length;i++){
                        String[] tblfieldArray=mFieldArray[i].split("@@");
                        String mTable=tblfieldArray[0];
                        String mFields=tblfieldArray[1];


                        JSONArray jsonArray=jsonObject.getJSONObject(mTable).getJSONArray(mFields);
                        String[] list=null;
                        String[] vlist=null;
                        if(jsonArray.size()>0) {//mContext.databaseList();
                            list=new String[jsonArray.size()];
                            vlist=new String[jsonArray.size()];
                            for (int j = 0; j < jsonArray.size(); j++) {
                                String value = jsonArray.getJSONObject(j).getString("value");
                                String label = jsonArray.getJSONObject(j).getString("label");
                                vlist[j] = value;
                                list[j] = label;

                            }
                            LattePreference.addCustomAppProfile(mFieldArray[i], JSONObject.toJSONString(list));
                            LattePreference.addCustomAppProfile(mFieldArray[i]+"_VAL", JSONObject.toJSONString(vlist));
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LatteLogger.d(e);
                super.onError(e);
            }
        });
        return resultMap;
    }

    public void getLxzbklxOption(final String mTblfields)
    {

        String[][] result=null;
        Observable<String> obj =
                RxRestClient.builder()
                        .url("getLxzbklxList")
                        .params("orgno", mUser.getOrgNo())
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONArray jsonArray=JSONArray.parseArray(result);
                    String[] list=null;
                    String[] vlist=null;
                    if(jsonArray.size()>0) {
                        list=new String[jsonArray.size()];
                        vlist=new String[jsonArray.size()];
                        for (int j = 0; j < jsonArray.size(); j++) {
                            String value = jsonArray.getJSONObject(j).getString("value");
                            String label = jsonArray.getJSONObject(j).getString("label");
                            vlist[j] = value;
                            list[j] = label;

                        }
                        LattePreference.addCustomAppProfile("LXZBKLX", JSONObject.toJSONString(list));
                        LattePreference.addCustomAppProfile("LXZBKLX_VAL", JSONObject.toJSONString(vlist));
                    }
                }
            }
        });
    }

    public void getLiemsOption(final String method,final String mTblfields)
    {
        getLiemsOption(method,mTblfields,"");
    }

    public void getLiemsOption(final String method,final String mTblfields,String params)
    {
        String[][] result=null;
        Observable<String> obj =
                RxRestClient.builder()
                        .url(method)
                        .params("orgno", mUser.getOrgNo())
                        .params("params", params)
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONArray jsonArray=JSONArray.parseArray(result);
                    String[] list=null;
                    String[] vlist=null;
                    if(jsonArray.size()>0) {
                        list=new String[jsonArray.size()];
                        vlist=new String[jsonArray.size()];
                        for (int j = 0; j < jsonArray.size(); j++) {
                            String value = jsonArray.getJSONObject(j).getString("value");
                            String label = jsonArray.getJSONObject(j).getString("label");
                            vlist[j] = value;
                            list[j] = label;
                        }
                    }
                    LattePreference.addCustomAppProfile(mTblfields, JSONObject.toJSONString(list));
                    LattePreference.addCustomAppProfile(mTblfields+"_VAL", JSONObject.toJSONString(vlist));
                }else{
                    LattePreference.removeCustomAppProfile(mTblfields);
                    LattePreference.removeCustomAppProfile(mTblfields+"_VAL");
                }
            }
        });
    }

    public void autoUpdateVersion()
    {
        String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        Observable<String> obj =
                RxRestClient.builder()
                        .url(BASE_URL.replaceAll("webservice/","apkfile/update.json"))
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONObject jsonObject=JSONObject.parseObject(result);
                    String serverVersion=jsonObject.getString("serverVersion");
                }
            }
        });
    }

    public void upLoadFile(String filePath,String tableName,String fileName)
    {
        String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        Observable<String> obj =
                RxRestClient.builder()
                        .url(BASE_URL.replaceAll("webservice/","upLoadFile"))
                        .file(filePath)
                        .params("tableName",tableName)
                        .params("fileName",fileName)
                        .loader(mContext)
                        .build()
                        .upload();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)&&"success".equals(result)) {
                    Toast.makeText(mContext,"上传成功！",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(mContext,result,Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void glideImage(BaseDelegate delegate, ImageView iView, String tableName, String imagePath,String key)
    {
        String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        String url=BASE_URL.replaceAll("webservice/","uploadfiles");
        GlideApp.with(delegate).load(url+"/"+tableName+"/"+imagePath).signature(new ObjectKey(key)).into(iView);
    }
}
