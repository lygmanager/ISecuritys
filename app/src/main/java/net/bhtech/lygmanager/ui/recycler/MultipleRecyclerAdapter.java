package net.bhtech.lygmanager.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.bhtech.lygmanager.isecuritys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 傅令杰
 */

public class MultipleRecyclerAdapter extends
        BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements
        BaseQuickAdapter.SpanSizeLookup{

    //确保初始化一次Banner，防止重复Item加载
    private boolean mIsInitBanner = false;


    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    public void refresh(List<MultipleItemEntity> data) {
        getData().clear();
        setNewData(data);
        notifyDataSetChanged();
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.DEFECT, R.layout.item_defectvlist);
        addItemType(ItemType.WORKSHEET, R.layout.item_worksheet);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_multiple, text);
                holder.setText(R.id.img_multiple, imageUrl);
                break;
            case ItemType.DEFECT:
                holder.setText(R.id.LIM_SHT, (String)entity.getField("LIM_SHT"));
                holder.setText(R.id.LIM_TYP, (String)entity.getField("LIM_TYP"));
                holder.setText(R.id.LIM_ID, (String)entity.getField("LIM_ID"));
                holder.setText(R.id.FUM_DTM, (String)entity.getField("FUM_DTM"));
                holder.setText(R.id.LIM_NO, (String)entity.getField("LIM_NO"));
                break;
            case ItemType.WORKSHEET:
                holder.setText(R.id.TTK_ID, (String)entity.getField("TTK_ID"));
                holder.setText(R.id.TTK_ADR, (String)entity.getField("TTK_ADR"));
                holder.setText(R.id.TTKPER_ID, (String)entity.getField("TTKPER_ID"));
                holder.setText(R.id.TTK_NO, (String)entity.getField("TTK_NO"));
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

}
