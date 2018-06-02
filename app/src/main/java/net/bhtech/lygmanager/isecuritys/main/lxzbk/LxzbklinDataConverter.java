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
            List<LxzbklinEntity>  ls=JSONArray.parseArray(lr.getRows().toString(),LxzbklinEntity.class);
            for (LxzbklinEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.LXZBKLIN)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JCLIN_NO", data.getJCLIN_NO())
                        .setField("JCLIN_DSC", data.getJCLIN_DSC())
                        .setField("JCLIN_SFNUM", data.getJCLIN_SFNUM())
                        .setField("JCLIN_FXNUM", data.getJCLIN_FXNUM())
                        .setField("JCLIN_ZDNUM", data.getJCLIN_ZDNUM())
                        .setField("JCLIN_NANUM", data.getJCLIN_NANUM())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
