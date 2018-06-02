package net.bhtech.lygmanager.isecuritys.main.index;

import android.content.Context;

import net.bhtech.lygmanager.ui.recycler.DataConverter;
import net.bhtech.lygmanager.ui.recycler.MultipleFields;
import net.bhtech.lygmanager.ui.recycler.MultipleItemEntity;
import net.bhtech.lygmanager.utils.file.LocalJsonResolutionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxinbiao on 2017/11/17.
 */

public class IndexDataConverter extends DataConverter {
    private Context mContext=null;
    private String mFileName="Index";
    public IndexDataConverter(Context context){
        mContext=context;
    }

    public IndexDataConverter(Context context,String fileName){
        mContext=context;
        mFileName=fileName;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        String foodJson = LocalJsonResolutionUtils.getJson(mContext, mFileName+"Data.json");
        final List<IndexDataEntity> dataArray = LocalJsonResolutionUtils.JsonToArray(foodJson,IndexDataEntity.class);

        for (IndexDataEntity data:dataArray) {
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, data.getItemType())
                    .setField(MultipleFields.SPAN_SIZE, data.getSpanSize())
                    .setField(MultipleFields.ID, data.getItemId())
                    .setField(MultipleFields.TEXT, data.getItemText())
                    .setField(MultipleFields.IMAGE_URL, data.getImageUrl())
                    .setField(MultipleFields.TEXT_COLOR, data.getTextColor())
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
