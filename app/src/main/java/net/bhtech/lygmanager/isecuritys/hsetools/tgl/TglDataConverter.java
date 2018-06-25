package net.bhtech.lygmanager.isecuritys.hsetools.tgl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.AqgckEntity;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2018/5/22.
 */

public class TglDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.TGL)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("TGL_NO", data.getString("TGL_NO"))
                        .setField("TGL_ID", data.getString("TGL_ID"))
                        .setField("TG_SHT", data.getString("TG_SHT"))
                        .setField("TG_USR_NAM", data.getString("TG_USR_NAM"))
                        .setField("TG_CBS_NAM", data.getString("TG_CBS_NAM"))
                        .setField("TG_USR", data.getString("TG_USR"))
                        .setField("TG_CBS", data.getString("TG_CBS"))
                        .setField("TG_QY", data.getString("TG_QY"))
                        .setField("TG_DTM", data.getString("TG_DTM"))
                        .setField("FGL_NUM", data.getString("FGL_NUM"))
                        .setField("VALID_STA", getTextTagInfo(data.getString("VALID_STA"),"HSETGLMST@@VALID_STA"))
                        .setField("VALID_STA_VAL", data.getString("VALID_STA"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
