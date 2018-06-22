package net.bhtech.lygmanager.ui.recycler;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        addItemType(ItemType.VDVEN, R.layout.item_vdvenvlist);
        addItemType(ItemType.TGL, R.layout.item_tglvlist);

        addItemType(ItemType.SMUSR, R.layout.item_smusrvlist);
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
                IconTextView tv_multiple=holder.getView(R.id.img_multiple);
                Random random = new Random();
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
//                tv_multiple.setTextColor(Color.rgb(r,g,b));
                AppCompatTextView tv_tasknum = holder.getView(R.id.tv_tasknum);
                AppCompatTextView tv_tasknum2 = holder.getView(R.id.tv_tasknum2);
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
                    tv_tasknum.setVisibility(View.GONE);
                    tv_tasknum2.setVisibility(View.GONE);
                }else {

                   int taskNum = entity.getField(MultipleFields.TASK_NUM);
                   if(taskNum>0) {
                       tv_tasknum.setVisibility(View.VISIBLE);
                       tv_tasknum.setText(taskNum+"");
                   }
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
                holder.setRightText(R.id.TTK_ID, (String)entity.getField("TTK_ID"));
                holder.setRightText(R.id.TTK_ADR, (String)entity.getField("TTK_ADR"));
                holder.setRightText(R.id.PLABEG_DTM, (String)entity.getField("PLABEG_DTM"));
                holder.setRightText(R.id.PLAEND_DTM, (String)entity.getField("PLAEND_DTM"));
                holder.setText(R.id.TTK_NO, (String)entity.getField("TTK_NO"));
                break;
            case ItemType.AQGCK:
                holder.setRightText(R.id.BGC_ORG, (String)entity.getField("BGC_ORG"));
                holder.setRightText(R.id.GC_TYP, (String)entity.getField("GC_TYP"));
                holder.setRightText(R.id.GC_SX, (String)entity.getField("GC_SX"));
                holder.setRightText(R.id.GC_DTM, (String)entity.getField("GC_DTM"));
                holder.setRightText(R.id.GC_RW, (String)entity.getField("GC_RW"));
                holder.setRightText(R.id.GC_QY, (String)entity.getField("GC_QY"));
                holder.setText(R.id.AQ_NO, (String)entity.getField("AQ_NO"));
                break;
            case ItemType.LXZBK:
                holder.setRightText(R.id.JCK_TYP, (String)entity.getField("JCK_TYP"));
                holder.setRightText(R.id.JCK_DTM, (String)entity.getField("JCK_DTM"));
                holder.setRightText(R.id.JCK_ADR, (String)entity.getField("JCK_ADR"));
                holder.setRightText(R.id.JCKUSR_ID, (String)entity.getField("JCKUSR_ID"));
                holder.setText(R.id.JCK_NO, (String)entity.getField("JCK_NO"));
                break;
            case ItemType.LXZBKLIN:
                holder.setText(R.id.JCLIN_NO, (String)entity.getField("JCLIN_NO"));
                holder.setText(R.id.JCLIN_DSC, (String)entity.getField("JCLIN_DSC"));
                holder.setText(R.id.JCLIN_SFNUM, (String)entity.getField("JCLIN_SFNUM"));
                holder.setText(R.id.JCLIN_FXNUM, (String)entity.getField("JCLIN_FXNUM"));
                holder.setText(R.id.JCLIN_ZDNUM, (String)entity.getField("JCLIN_ZDNUM"));
                holder.setText(R.id.VALID_STA, (String)entity.getField("VALID_STA"));
                break;
            case ItemType.BGB:
                holder.setRightText(R.id.BG_NOT, (String)entity.getField("BG_NOT"));
                holder.setRightText(R.id.BG_ADR, (String)entity.getField("BG_ADR"));
                holder.setRightText(R.id.JC_DTM, (String)entity.getField("JC_DTM"));
                holder.setRightText(R.id.CST_NO, (String)entity.getField("CST_NO"));
                holder.setRightText(R.id.GLUSR_ID, (String)entity.getField("GLUSR_ID"));
                holder.setText(R.id.BGB_NO, (String)entity.getField("BGB_NO"));
                break;
            case ItemType.VDVEN:
                holder.setRightText(R.id.VENDOR_NAM, (String)entity.getField("VENDOR_NAM"));
                holder.setRightText(R.id.MANAGER_USR, (String)entity.getField("MANAGER_USR"));
                holder.setRightText(R.id.USR_NAM, (String)entity.getField("USR_NAM"));
                holder.setText(R.id.VEN_NO, (String)entity.getField("VEN_NO"));
                break;
            case ItemType.TGL:
                holder.setRightText(R.id.TGL_ID, (String)entity.getField("TGL_ID"));
                holder.setRightText(R.id.TG_SHT, (String)entity.getField("TG_SHT"));
                holder.setRightText(R.id.TG_USR, (String)entity.getField("TG_USR_NAM"));
                holder.setRightText(R.id.TG_CBS, (String)entity.getField("TG_CBS_NAM"));
                holder.setRightText(R.id.TG_DTM, (String)entity.getField("TG_DTM"));
                holder.setRightText(R.id.VALID_STA, (String)entity.getField("VALID_STA"));
                holder.setText(R.id.TGL_NO, (String)entity.getField("TGL_NO"));
                break;
            case ItemType.SMUSR:
                holder.setRightText(R.id.USR_ID, (String)entity.getField("USR_ID"));
                holder.setRightText(R.id.USR_NAM, (String)entity.getField("USR_NAM"));
                holder.setRightText(R.id.MOBILE_PHONE, (String)entity.getField("MOBILE_PHONE"));
                holder.setRightText(R.id.USR_BIRTH, (String)entity.getField("USR_BIRTH"));
                holder.setRightText(R.id.IDENTITY_NO, (String)entity.getField("IDENTITY_NO"));
                holder.setRightText(R.id.USR_PEO, (String)entity.getField("USR_PEO"));
                holder.setRightText(R.id.VEN_NO, (String)entity.getField("VEN_NO"));
                holder.setRightText(R.id.POS_NO, (String)entity.getField("POS_NO"));
                holder.setRightText(R.id.USR_SEX, (String)entity.getField("USR_SEX"));
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
