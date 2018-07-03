package net.bhtech.lygmanager.isecuritys.hsetools.gfxjd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.util.ArrayList;

/**
 * Created by zhangxinbiao on 2018/5/22.
 */

public class GfxjdlinDataConverter extends DataConverter {
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
                        .setField(MultipleFields.ITEM_TYPE, ItemType.GFXJDLIN)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JDLIN_NO", data.getString("JDLIN_NO"))
                        .setField("JDLIN_DESC", data.getString("JDLIN_DESC"))
                        .setField("JDLIN_JL", getTextTagInfo(data.getString("JDLIN_JL"),"SFGFXJDLIN@@JDLIN_JL"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
