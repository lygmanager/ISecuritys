package net.bhtech.lygmanager.isecuritys.main.equip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.FirstttkEntity;
import net.bhtech.lygmanager.database.LxzbkEntity;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.ItemType;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.log.LatteLogger;

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
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            LatteLogger.d(lr.getRows());
            if(lr.getRows()!=null) {
                List<FirstttkEntity> ls = JSONArray.parseArray(lr.getRows().toString(), FirstttkEntity.class);
                for (FirstttkEntity data : ls) {
                    final MultipleItemEntity entity = MultipleItemEntity.builder()
                            .setField(MultipleFields.ITEM_TYPE, ItemType.WORKSHEET)
                            .setField(MultipleFields.SPAN_SIZE, 1)
                            .setField("TTK_ID", data.getTTK_ID())
                            .setField("TTK_ADR", data.getTTK_ADR())
                            .setField("TTKPER_DSC_ID", data.getTTKPER_DSC_ID())
                            .setField("PLABEG_DTM", data.getPLABEG_DTM())
                            .setField("PLAEND_DTM", data.getPLAEND_DTM())
                            .setField("TTK_NO", data.getTTK_NO())
                            .build();
                    ENTITIES.add(entity);
                }
            }
        }
        return ENTITIES;
    }
}
