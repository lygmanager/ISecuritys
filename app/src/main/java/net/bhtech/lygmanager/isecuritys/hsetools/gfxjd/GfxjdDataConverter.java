package net.bhtech.lygmanager.isecuritys.hsetools.gfxjd;

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

public class GfxjdDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.GFXJD)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JD_NO", data.getString("JD_NO"))
                        .setField("PLA_NO", data.getString("PLA_NO"))
                        .setField("CRW_NO", data.getString("CRW_NO"))
                        .setField("JD_ITEM", data.getString("JD_ITEM"))
                        .setField("JD_DESC", data.getString("JD_DESC"))
                        .setField("JD_ADR", data.getString("JD_ADR"))
                        .setField("JD_USR", data.getString("JD_USR"))
                        .setField("JD_REPORT", data.getString("JD_REPORT"))
                        .setField("JD_DPTUSR", data.getString("JD_DPTUSR"))
                        .setField("ZY_NO", data.getString("ZY_NO"))
                        .setField("START_DTM", data.getString("START_DTM"))
                        .setField("END_DTM", data.getString("END_DTM"))
                        .setField("ZY_ID", data.getString("ZY_ID"))
                        .setField("JD_USR_NAM", data.getString("JD_USR_NAM"))
                        .setField("JD_DPTUSR_NAM", data.getString("JD_DPTUSR_NAM"))
                        .setField("VALID_STA", data.getString("VALID_STA"))
                        .setField("PLA_NAM", data.getString("PLA_NAM"))
                        .setField("CRW_NAM", data.getString("CRW_NAM"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
