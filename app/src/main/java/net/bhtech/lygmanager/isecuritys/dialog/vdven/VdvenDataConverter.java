package net.bhtech.lygmanager.isecuritys.dialog.vdven;

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

public class VdvenDataConverter  extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray  ls=JSONArray.parseArray(lr.getRows().toString());
            for (int i=0;i<ls.size();i++) {
                JSONObject data = ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.VDVEN)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("VEN_NO", data.getString("VEN_NO"))
                        .setField("VENWF_STA", data.getString("VENWF_STA"))
                        .setField("VENDOR_NAM", data.getString("VENDOR_NAM"))
                        .setField("MANAGER_USR", data.getString("MANAGER_USR"))
                        .setField("USR_NAM", data.getString("USR_NAM"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
