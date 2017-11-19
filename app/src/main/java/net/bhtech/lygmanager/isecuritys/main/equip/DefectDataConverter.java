package net.bhtech.lygmanager.isecuritys.main.equip;

import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.database.DefectEntity;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class DefectDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            List<DefectEntity> ls = JSONArray.parseArray(jsonArray,DefectEntity.class);
            for (DefectEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.DEFECT)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("LIM_ID", data.getLIM_ID())
                        .setField("LIM_SHT", data.getLIM_SHT())
                        .setField("LIM_TYP", data.getLIM_TYP())
                        .setField("LIM_NO", data.getLIM_NO())
                        .setField("FUM_DTM", data.getFUM_DTM())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
