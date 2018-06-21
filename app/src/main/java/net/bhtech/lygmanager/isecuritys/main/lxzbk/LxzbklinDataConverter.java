package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.AqgckEntity;
import net.bhtech.lygmanager.database.LxzbklinEntity;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2018/5/22.
 */

public class LxzbklinDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray ls=JSONArray.parseArray(lr.getRows().toString());
            for (int i=0;i< ls.size();i++) {
                JSONObject data=ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.LXZBKLIN)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JCLIN_NO", data.getString("JCLIN_NO"))
                        .setField("JCLIN_DSC", data.getString("JCLIN_DSC"))
                        .setField("JCLIN_SFNUM", data.getString("JCLIN_SFNUM"))
                        .setField("JCLIN_FXNUM", data.getString("JCLIN_FXNUM"))
                        .setField("JCLIN_ZDNUM", data.getString("JCLIN_ZDNUM"))
                        .setField("VALID_STA", getTextTagInfo(data.getString("VALID_STA"),"SDZBJCKLIN@@VALID_STA"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
