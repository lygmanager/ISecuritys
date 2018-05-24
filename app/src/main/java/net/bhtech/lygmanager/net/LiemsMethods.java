package net.bhtech.lygmanager.net;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
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
                            resultMap.put(mFields,list);
                            resultMap.put(mFields+"_VAL",vlist);
                        }
                    }


                }
            }
        });
        return resultMap;
    }
}
