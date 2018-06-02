package net.bhtech.lygmanager.isecuritys.main.aqgck;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.database.AqgckEntity;
import net.bhtech.lygmanager.database.DefectEntity;
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

public class AqgckDataConverter  extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            List<AqgckEntity>  ls=JSONArray.parseArray(lr.getRows().toString(),AqgckEntity.class);
            for (AqgckEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.AQGCK)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("AQ_NO", data.getAQ_NO())
                        .setField("GC_ORG", getTextTagInfo(data.getGC_ORG(),"AQGCKMST@@GC_ORG"))
                        .setField("GC_CST", getTextTagInfo(data.getGC_CST(),"AQGCKMST@@GC_CST"))
                        .setField("GC_NAM", data.getGC_NAM())
                        .setField("GC_DTM", data.getGC_DTM())
                        .setField("BGC_ORG", getTextTagInfo(data.getBGC_ORG(),"AQGCKMST@@BGC_ORG"))
                        .setField("GC_RW", data.getGC_RW())
                        .setField("GC_QY", data.getGC_QY())
                        .setField("GC_SX", getTextTagInfo(data.getGC_SX(),"AQGCKMST@@GC_SX"))
                        .setField("GC_TYP", getTextTagInfo(data.getGC_TYP(),"AQGCKMST@@GC_TYP"))
                        .setField("WX_SHT", data.getWX_SHT())
                        .setField("IS_JZ", getTextTagInfo(data.getIS_JZ(),"AQGCKMST@@IS_JZ"))
                        .setField("JZ_CS", data.getJZ_CS())
                        .setField("JZ_XD", data.getJZ_XD())
                        .setField("FX_TYP", getTextTagInfo(data.getFX_TYP(),"AQGCKMST@@FX_TYP"))
                        .setField("ZQX_SHT", data.getZQX_SHT())
                        .setField("ORG_NO", data.getORG_NO())
                        .setField("BZ_NO", data.getBZ_NO())
                        .setField("USR_ID", data.getUSR_ID())
                        .setField("USR_DTM", data.getUSR_DTM())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
