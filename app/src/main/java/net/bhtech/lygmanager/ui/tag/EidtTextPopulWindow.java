package net.bhtech.lygmanager.ui.tag;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wang.avi.AVLoadingIndicatorView;

import net.bhtech.lygmanager.database.AqgckEntity;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2018/5/23.
 */

public class EidtTextPopulWindow {

    private Context mContext=null;
    private EditText mEditText=null;
    private ListPopupWindow mListPopupWindow=null;
    private String mOrgNo=null;
    private String mUserId=null;
    private String mTblfields=null;
    private String mTable=null;
    private String mField=null;
    private String[][] dataList=null;

    public static EidtTextPopulWindow create(Context context,EditText editText){

        final EidtTextPopulWindow mEidtTextPopulWindow = new EidtTextPopulWindow();
        mEidtTextPopulWindow.mContext=context;
        mEidtTextPopulWindow.mEditText=editText;
        return mEidtTextPopulWindow;

    }

    public EidtTextPopulWindow params(String orgNo,String userId,String tblfields){
        EidtTextPopulWindow mEidtTextPopulWindow =create(mContext,mEditText);
        mEidtTextPopulWindow.mOrgNo=orgNo;
        mEidtTextPopulWindow.mUserId=userId;
        mEidtTextPopulWindow.mTblfields=tblfields;
        String[] tblfieldArray=tblfields.split("@@");
        mEidtTextPopulWindow.mTable=tblfieldArray[0];
        mEidtTextPopulWindow.mField=tblfieldArray[1];
        return mEidtTextPopulWindow;
    }

    public void showListPopulWindow(){
        Observable<String> obj =
                RxRestClient.builder()
                        .url("getFieldOption")
                        .params("orgno", mOrgNo)
                        .params("userid", mUserId)
                        .params("tblfields",mTblfields)
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                    JSONArray jsonArray=jsonObject.getJSONObject(mTable).getJSONArray(mField);
                    String[] list=null;
                    String[] vlist=null;
                    if(jsonArray.size()>0) {
                        list=new String[jsonArray.size()];
                        vlist=new String[jsonArray.size()];
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String value = jsonArray.getJSONObject(i).getString("value");
                            String label = jsonArray.getJSONObject(i).getString("label");
                            vlist[i] = value;
                            list[i] = label;
                        }
                        initListPopulWindow(vlist,list);
                    }

                }
            }
        });

    }

    public void initListPopulWindow(final String[] vlist,final String[] list) {
        mListPopupWindow = new ListPopupWindow(mContext);
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(mContext,
                R.layout.tag_list_item, list));
        mListPopupWindow.setAnchorView(mEditText);
        mListPopupWindow.setModal(true);

        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEditText.setText(list[position]);
                mEditText.setTag(vlist[position]);
                mListPopupWindow.dismiss();
            }
        });
        mListPopupWindow.show();
    }

    public void initListPopulWindow(final String tblfieldArray) {
        String labelStr= LattePreference.getCustomAppProfile(tblfieldArray);
        if(labelStr==null||"".equals(labelStr))
        {
            return;
        }
        JSONArray labels2=JSONArray.parseArray(labelStr);
        String[] tmp=new String [labels2.size()];
        for (int i=0;i<labels2.size();i++)
        {
            tmp[i]=labels2.getString(i);
        }
        final String[] labels=tmp;
        String valueStr=LattePreference.getCustomAppProfile(tblfieldArray+"_VAL");
        if(valueStr==null||"".equals(valueStr))
        {
            return;
        }
        final JSONArray values2=JSONArray.parseArray(valueStr);
        String[] tmp2=new String [values2.size()];
        for (int i=0;i<values2.size();i++)
        {
            tmp2[i]=values2.getString(i);
        }
        final String[] values=tmp2;

        mListPopupWindow = new ListPopupWindow(mContext);
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(mContext,
                R.layout.tag_list_item, labels));
        mListPopupWindow.setAnchorView(mEditText);
        mListPopupWindow.setModal(true);

        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEditText.setText(labels[position]);
                mEditText.setTag(values[position]);
                mListPopupWindow.dismiss();
            }
        });
        mListPopupWindow.show();
    }
}
