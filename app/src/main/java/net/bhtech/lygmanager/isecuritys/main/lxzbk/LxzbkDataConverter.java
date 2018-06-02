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
        String jsonArray=getJsonData();LatteLogger.d(jsonArray);
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            List<LxzbkEntity>  ls=JSONArray.parseArray(lr.getRows().toString(),LxzbkEntity.class);
            for (LxzbkEntity data : ls) {
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.LXZBK)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("JCK_TYP", getTextTagInfo(data.getJCK_TYP(),"LXZBKLX"))
                        .setField("JCK_DTM", data.getJCK_DTM())
                        .setField("JCKCST_NO", data.getJCKCST_NO())
                        .setField("CRW_NO", data.getCRW_NO())
                        .setField("JCK_ADR", data.getJCK_ADR())
                        .setField("JCK_DSC", data.getJCK_DSC())
                        .setField("JCKUSR_ID", data.getJCKUSR_ID())
                        .setField("ORG_NO", data.getORG_NO())
                        .setField("JCK_NO", data.getJCK_NO())
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
