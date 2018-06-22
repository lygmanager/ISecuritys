package net.bhtech.lygmanager.isecuritys.hsetools.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by zhangxinbiao on 2018/5/22.
 */

public class SmusrDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray  ls=JSONArray.parseArray(lr.getRows().toString());
            for (int i=0;i<ls.size();i++)
            {
                JSONObject data = ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.SMUSR)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("USR_ID", data.getString("USR_ID"))
                        .setField("USR_NAM", data.getString("USR_NAM"))
                        .setField("MOBILE_PHONE", data.getString("MOBILE_PHONE"))
                        .setField("USR_BIRTH", data.getString("USR_BIRTH"))
                        .setField("IDENTITY_NO", data.getString("IDENTITY_NO"))
                        .setField("USR_PEO", data.getString("USR_PEO"))
                        .setField("VEN_NO", data.getString("VEN_NO"))
                        .setField("POS_NO", data.getString("POS_NO"))
                        .setField("VEN_TYP", getTextTagInfo(data.getString("VEN_TYP"),"SMUSRMST@@VEN_TYP"))
                        .setField("USR_SEX", getTextTagInfo(data.getString("USR_SEX"),"SMUSRMST@@USR_SEX"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
