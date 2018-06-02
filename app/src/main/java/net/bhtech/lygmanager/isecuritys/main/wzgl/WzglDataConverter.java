package net.bhtech.lygmanager.isecuritys.main.wzgl;

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

public class WzglDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        LatteLogger.d(jsonArray);
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray  ls=JSONArray.parseArray(lr.getRows().toString());
            for (int i=0;i< ls.size();i++) {
                JSONObject data=ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.BGB)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("BGB_NO", data.getString("BGB_NO"))
                        .setField("JC_DTM", data.getString("JC_DTM"))
                        .setField("BG_NOT", data.getString("BG_NOT"))
                        .setField("BG_ADR", getTextTagInfo(data.getString("BG_ADR"),"RMBGBMST@@BG_ADR"))
                        .setField("BF_TYP", getTextTagInfo(data.getString("BF_TYP"),"RMBGBMST@@BF_TYP"))
                        .setField("JCUSR_ID", data.getString("JCUSR_ID"))
                        .setField("CST_NO", getTextTagInfo(data.getString("CST_NO"),"BgbcstOption"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
