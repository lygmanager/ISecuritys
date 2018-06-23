package net.bhtech.lygmanager.isecuritys.hsetools.fgl;

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

public class FglDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.FGL)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("FGL_NO", data.getString("FGL_NO"))
                        .setField("TGL_NO", data.getString("TGL_NO"))
                        .setField("TGL_ID", data.getString("TGL_ID"))
                        .setField("TG_SHT", data.getString("TG_SHT"))
                        .setField("CBSPRJ_MAN_NAM", data.getString("CBSPRJ_MAN_NAM"))
                        .setField("TG_CBS_NAM", data.getString("TG_CBS_NAM"))
                        .setField("PRJ_NO", data.getString("PRJ_NO"))
                        .setField("TG_CBS", data.getString("TG_CBS"))
                        .setField("CBSHSE_MAN_NAM", data.getString("CBSHSE_MAN_NAM"))
                        .setField("TG_QY", data.getString("TG_QY"))
                        .setField("JZ_SHT", data.getString("JZ_SHT"))
                        .setField("IS_JZ", data.getString("IS_JZ"))
                        .setField("VALID_STA", getTextTagInfo(data.getString("VALID_STA"),"HSEFGLMST@@VALID_STA"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
