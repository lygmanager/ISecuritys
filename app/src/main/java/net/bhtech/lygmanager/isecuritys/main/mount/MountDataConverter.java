package net.bhtech.lygmanager.isecuritys.main.mount;

import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.database.MountEntity;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class MountDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            List<MountEntity> ls = JSONArray.parseArray(jsonArray,MountEntity.class);
            for (MountEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.MOUNT)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("MOUNT_NO", data.getMOUNT_NO())
                        .setField("ORG_NO", data.getORG_NO())
                        .setField("MOUNT_DSC", data.getMOUNT_DSC())
                        .setField("ELC_ID", data.getELC_ID())
                        .setField("MOUNT_IP", data.getMOUNT_IP())
                        .setField("MOUNT_DTM", data.getMOUNT_DTM())
                        .setField("USR_NAM", data.getUSR_NAM())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
