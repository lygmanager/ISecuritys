package net.bhtech.lygmanager.isecuritys.common.workflow;

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

public class WorkflowDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ENTITIES.clear();
        String jsonArray=getJsonData();
        LatteLogger.d(jsonArray);
        if(jsonArray!=null&&!"".equals(jsonArray)) {
            JSONObject lr2= (JSONObject) JSONObject.parse(jsonArray);
            String historyNodes=lr2.getString("historyNodes");
//            LiemsResult lr=JSONObject.toJavaObject(lr2,LiemsResult.class);
            JSONArray  ls=JSONArray.parseArray(historyNodes);
            for (int i=0;i<ls.size();i++)
            {
                JSONObject data = ls.getJSONObject(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, ItemType.WORKFLOW)
                        .setField(MultipleFields.SPAN_SIZE, 1)
                        .setField("OBJECT_SHT", data.getString("nodeSht"))
                        .setField("DEAL_RESULT", data.getString("operNam"))
                        .setField("USR_NAM", data.getString("operUsrNam"))
                        .setField("MEMO_TXT", data.getString("operContent"))
                        .setField("OPERDTM", data.getString("operDtm"))
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
