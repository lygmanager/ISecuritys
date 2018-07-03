package net.bhtech.lygmanager.isecuritys.hsetools.gfxzy;

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

public class GfxzyDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.GFXZY)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("ZY_NO", data.getString("ZY_NO"))
                        .setField("ZY_ID", data.getString("ZY_ID"))
                        .setField("XK_JB", getTextTagInfo(data.getString("XK_JB"),"GFXZYXKMST@@XK_JB"))
                        .setField("ZY_TYP", data.getString("ZY_TYP"))
                        .setField("ZY_DW", data.getString("ZY_DW"))
                        .setField("CRW_NO", data.getString("CRW_NO"))
                        .setField("ZT_QY", data.getString("ZT_QY"))
                        .setField("ZY_SHT", data.getString("ZY_SHT"))
                        .setField("STA_DTM", data.getString("STA_DTM"))
                        .setField("ZY_TYP_STR", data.getString("ZY_TYP_STR"))
                        .setField("END_DTM", data.getString("END_DTM"))
                        .setField("JD_NUM", data.getString("JD_NUM"))
                        .setField("CRW_NAM", data.getString("CRW_NAM"))
                        .setField("PLA_NAM", data.getString("PLA_NAM"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
