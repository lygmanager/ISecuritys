package net.bhtech.lygmanager.isecuritys.main.lxzbk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.LxzbkEntity;
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

public class LxzbkDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray  ls=JSONArray.parseArray(lr.getRows().toString());
            for (int i=0 ;i< ls.size();i++) {
                JSONObject data=ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.LXZBK)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JCK_TYP", getTextTagInfo(data.getString("JCK_TYP"),"LXZBKLX"))
                        .setField("JCK_DTM", data.getString("JCK_DTM"))
                        .setField("JCKCST_NO", data.getString("JCKCST_NO"))
                        .setField("CRW_NO", data.getString("CRW_NO"))
                        .setField("JCK_ADR", data.getString("JCK_ADR"))
                        .setField("JCK_DSC", data.getString("JCK_DSC"))
                        .setField("JCKUSR_ID", data.getString("JCKUSR_NAM"))
                        .setField("ORG_NO", data.getString("ORG_NO"))
                        .setField("JCK_NO", data.getString("JCK_NO"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
