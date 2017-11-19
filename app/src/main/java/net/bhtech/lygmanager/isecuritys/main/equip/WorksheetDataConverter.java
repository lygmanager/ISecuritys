package net.bhtech.lygmanager.isecuritys.main.equip;

import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.database.FirstttkEntity;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class WorksheetDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            List<FirstttkEntity> ls = JSONArray.parseArray(jsonArray,FirstttkEntity.class);
            for (FirstttkEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.WORKSHEET)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("TTK_ID", data.getTTK_ID())
                        .setField("TTK_ADR", data.getTTK_ADR())
                        .setField("TTKPER_ID", data.getTTKPER_ID())
                        .setField("TTK_NO", data.getTTK_NO())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
