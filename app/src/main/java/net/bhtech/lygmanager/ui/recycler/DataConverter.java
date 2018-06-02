package net.bhtech.lygmanager.ui.recycler;

import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITIES.clear();
    }

    public String getTextTagInfo(String text,String option){
        if(text==null||"".equals(text))
        {
            return "";
        }

        String labelStr= LattePreference.getCustomAppProfile(option);
        if(labelStr==null||"".equals(labelStr))
        {
            return text;
        }
        JSONArray labels2=JSONArray.parseArray(labelStr);

        String valueStr=LattePreference.getCustomAppProfile(option+"_VAL");
        if(valueStr==null||"".equals(valueStr))
        {
            return text;
        }
        final JSONArray values2=JSONArray.parseArray(valueStr);
        for (int i=0;i<values2.size();i++)
        {
            if(values2.getString(i).equals(text))
            {
                return labels2.getString(i);
            }
        }
        return text;
    }
}
