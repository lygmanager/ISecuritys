package net.bhtech.lygmanager.ui.recycler;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;

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
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_mainscreen);
        addItemType(ItemType.DEFECT, R.layout.item_defectvlist);
        addItemType(ItemType.WORKSHEET, R.layout.item_worksheet);
        addItemType(ItemType.AQGCK, R.layout.item_aqgckvlist);
        addItemType(ItemType.LXZBK, R.layout.item_lxzbkvlist);
        addItemType(ItemType.LXZBKLIN, R.layout.item_lxzbklinvlist);
        addItemType(ItemType.BGB, R.layout.item_bgbvlist);
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
               if((int)entity.getField(MultipleFields.SPAN_SIZE)==3)
                {
                    IconTextView img_title=holder.getView(R.id.img_title);
                    img_title.setText(imageUrl);
                    holder.getView(R.id.indexLayoutTitle).setVisibility(View.VISIBLE);
                    IconTextView iconTextView2=holder.getView(R.id.img_multiple2);
                    iconTextView2.setText(text);
                    iconTextView2.setTextColor(Color.GRAY);
                    holder.getView(R.id.img_multiple).setVisibility(View.GONE);
                    holder.getView(R.id.tv_multiple).setVisibility(View.GONE);
                }
                if("C".equals(entity.getField(MultipleFields.TEXT_COLOR)))
                {
                    IconTextView iconTextView=holder.getView(R.id.img_multiple);
                    iconTextView.setTextColor(0xFF00C6FF);
                }
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
                holder.setText(R.id.PLABEG_DTM, (String)entity.getField("PLABEG_DTM"));
                holder.setText(R.id.PLAEND_DTM, (String)entity.getField("PLAEND_DTM"));
                holder.setText(R.id.TTK_NO, (String)entity.getField("TTK_NO"));
                break;
            case ItemType.AQGCK:
                holder.setText(R.id.GC_ORG, (String)entity.getField("GC_ORG"));
                holder.setText(R.id.GC_NAM, (String)entity.getField("GC_NAM"));
                holder.setText(R.id.GC_DTM, (String)entity.getField("GC_DTM"));
                holder.setText(R.id.GC_RW, (String)entity.getField("GC_RW"));
                holder.setText(R.id.AQ_NO, (String)entity.getField("AQ_NO"));
                break;
            case ItemType.LXZBK:
                holder.setText(R.id.JCK_TYP, (String)entity.getField("JCK_TYP"));
                holder.setText(R.id.JCK_DTM, (String)entity.getField("JCK_DTM"));
                holder.setText(R.id.JCK_ADR, (String)entity.getField("JCK_ADR"));
                holder.setText(R.id.JCK_DSC, (String)entity.getField("JCK_DSC"));
                holder.setText(R.id.JCKUSR_ID, (String)entity.getField("JCKUSR_ID"));
                holder.setText(R.id.JCK_NO, (String)entity.getField("JCK_NO"));
                break;
            case ItemType.LXZBKLIN:
                holder.setText(R.id.JCLIN_NO, (String)entity.getField("JCLIN_NO"));
                holder.setText(R.id.JCLIN_DSC, (String)entity.getField("JCLIN_DSC"));
                holder.setText(R.id.JCLIN_SFNUM, (String)entity.getField("JCLIN_SFNUM"));
                holder.setText(R.id.JCLIN_FXNUM, (String)entity.getField("JCLIN_FXNUM"));
                holder.setText(R.id.JCLIN_ZDNUM, (String)entity.getField("JCLIN_ZDNUM"));
                holder.setText(R.id.JCLIN_NANUM, (String)entity.getField("JCLIN_NANUM"));
                break;
            case ItemType.BGB:
                holder.setText(R.id.BG_NOT, (String)entity.getField("BG_NOT"));
                holder.setText(R.id.BG_ADR, (String)entity.getField("BG_ADR"));
                holder.setText(R.id.JC_DTM, (String)entity.getField("JC_DTM"));
                holder.setText(R.id.CST_NO, (String)entity.getField("CST_NO"));
                holder.setText(R.id.BGB_NO, (String)entity.getField("BGB_NO"));
                holder.setText(R.id.BG_ADR, (String)entity.getField("BG_ADR"));
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
