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

public class CertifDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.CERTIF)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("USR_ID", data.getString("USR_ID"))
                        .setField("USR_NAM", data.getString("USR_NAM"))
                        .setField("CERTIF_TYP", data.getString("CERTIF_TYP"))
                        .setField("CERTIF_YXDTM", data.getString("CERTIF_YXDTM"))
                        .setField("CERTIF_FSDTM", data.getString("CERTIF_FSDTM"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
