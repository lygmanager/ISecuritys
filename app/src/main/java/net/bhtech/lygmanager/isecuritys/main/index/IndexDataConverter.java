package net.bhtech.lygmanager.isecuritys.main.index;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.file.LocalJsonResolutionUtils;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/17.
 */

public class IndexDataConverter extends DataConverter {
    private Context mContext=null;
    private String mFileName="Index";
    private UtusrEntity mUser=null;
    public IndexDataConverter(Context context){
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public IndexDataConverter(Context context,String fileName){
        mContext=context;
        mFileName=fileName;
        mUser= AccountManager.getSignInfo();
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        String foodJson = LocalJsonResolutionUtils.getJson(mContext, mFileName+"Data.json");
        final List<IndexDataEntity> dataArray = LocalJsonResolutionUtils.JsonToArray(foodJson,IndexDataEntity.class);
        String result=getJsonData();
        JSONObject jsonArray=JSONObject.parseObject(result);
        for (IndexDataEntity data:dataArray) {
            if(jsonArray.get("A"+data.getItemId())!=null)
                data.setTaskNum(jsonArray.getInteger("A"+data.getItemId()));
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, data.getItemType())
                    .setField(MultipleFields.SPAN_SIZE, data.getSpanSize())
                    .setField(MultipleFields.ID, data.getItemId())
                    .setField(MultipleFields.TEXT, data.getItemText())
                    .setField(MultipleFields.IMAGE_URL, data.getImageUrl())
                    .setField(MultipleFields.TEXT_COLOR, data.getTextColor())
                    .setField(MultipleFields.TASK_NUM, data.getTaskNum())
                    .build();

            ENTITIES.add(entity);
        }


        return ENTITIES;
    }
}
