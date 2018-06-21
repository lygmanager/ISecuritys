package net.bhtech.lygmanager.ui.tag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.delegates.BaseDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.ui.date.DatePickDialogUtil;
import net.bhtech.lygmanager.ui.date.DateTimePickDialogUtil;
import net.bhtech.lygmanager.ui.tree.CstUserDialog;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;


/**
 * @author luyg
 *
 */
public class RightAndLeftEditText extends LinearLayout {

    private LinearLayout rootView ;
    /** 标题 */
    private TextView textView;
    /** 编辑框 */
    private EditText editText;
    /** 线 */
    private View lineView;
    /** 错误提示框 */
    private TextView error;
    /** 失去焦点的线颜色 */
    private int normalColor=R.color.line_input_nomal;
    /** 得到焦点线的颜色 */
    private int selectColor= R.color.line_input_select;

    /**回调*/
    private IEditCall iCall;

    /**处理替代密码获取到焦点时候，直接清空的回调*/
    private IFouceCall iFouceCall ;

    /**编辑框的内容是否合法*/
    private boolean isLegal =false;

    public interface IEditCall {
        /**失去焦点或者输入完毕*/
        void cheack(String text);
    }

    /**获取到焦点的时候触发*/
    public interface IFouceCall{
        void fouce(String text);
    }


    public RightAndLeftEditText(Context context) {
        super(context);
        init(context, null);
    }

    public RightAndLeftEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("NewApi")
    public RightAndLeftEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        // 留意，父组件是this,不是空
        View allView = inflate(context, R.layout.tag_rightandleftedittext, this);
        rootView = (LinearLayout)allView.findViewById(R.id.line_top);
        lineView = (View) allView.findViewById(R.id.line);
        editText = (EditText) allView.findViewById(R.id.edit);
        textView = (TextView) allView.findViewById(R.id.text);
        error = (TextView) allView.findViewById(R.id.error);
        initAttribute(attrs);
        setLister();
    }


    /**
     * @param attrs : 配置属性
     * 解析属性
     */
    private void initAttribute(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.RightAndLeftEditText);
            String text = a.getString(R.styleable.RightAndLeftEditText_nameText);
            int size = a.getDimensionPixelSize(R.styleable.RightAndLeftEditText_nameSize, 0);
            if (size > 0) {
                // 设置组件的大小
                ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                layoutParams.width = size;
                textView.setLayoutParams(layoutParams);
            }

            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            }

            boolean editAble = a.getBoolean(R.styleable.RightAndLeftEditText_editAble,true);
            setEditTextEditAble(editAble);
//            editText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DatePickDialogUtil datePickDialogUtil=new DatePickDialogUtil(getContext().,null);
//                    datePickDialogUtil.dateTimePicKDialog(editText);
//                }
//            });

        }

        boolean init = editText.isFocused();
        setLineBg(init);
    }

    public void setPopChange()
    {

    }


    /**
     *  设置监听
     */
    public void setLister(){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setLineBg(hasFocus);
                if (hasFocus) {
                    //获取焦点，影藏错误信息
                    error.setVisibility(View.GONE);
                    //得到焦点的回调
                    if (iFouceCall!=null) {
                        iFouceCall.fouce(getEditTextInfo());
                    }
                }else {
                    //失去焦点时候在校验
                    if (iCall!=null) {
                        iCall.cheack(getEditTextInfo());
                    }
                }
            }
        });

        // 点击到这一行任何位置，编辑框都要设置焦点
        rootView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View paramView) {
                setLineBg(true);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.findFocus();
                error.setVisibility(View.GONE);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence paramCharSequence,
                                      int paramInt1, int paramInt2, int paramInt3) {

            }

            @Override
            public void beforeTextChanged(CharSequence paramCharSequence,
                                          int paramInt1, int paramInt2, int paramInt3) {

            }

            @Override
            public void afterTextChanged(Editable paramEditable) {
                if (iCall!=null) {
                    iCall.cheack(paramEditable.toString());
                }
            }
        });

    }
    /**
     * 设置线的颜色
     */
    public void setLineBg(boolean focus) {

        if (focus) {
            lineView.setBackgroundColor(getResources().getColor(selectColor));
        } else {
            lineView.setBackgroundColor(getResources().getColor(normalColor));
        }
        ViewGroup.LayoutParams lp = lineView.getLayoutParams();
        lp.height = focus ? 3 : 1;
        lineView.setLayoutParams(lp);
    }


    public View getRootView(){

        return rootView ;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }



    public void setEditTextEditAble(boolean flag) {
        if(flag){
            this.editText.setFocusable(true);
            this.editText.setCursorVisible(true);
            this.editText.setFocusableInTouchMode(true);
            this.editText.requestFocus();
        }else {
            this.editText.setCursorVisible(false);
            this.editText.setFocusable(false);
            this.editText.setFocusableInTouchMode(false);
        }
    }

    public IEditCall getiCall() {
        return iCall;
    }

    public void setiCall(IEditCall iCall) {
        this.iCall = iCall;
    }

    public void showErrMsg(String msg) {
        isLegal = false ;
        error.setVisibility(View.VISIBLE);
        error.setText(msg);
    }


    public void setLegal(){
        error.setVisibility(View.GONE);
        isLegal = true ;
    }

    /**
     * @param len : 输入框输入数据长度
     */
    public void limitInput(int len){
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len)});
    }

    public boolean isLegal(){
        return isLegal;
    }

    public boolean isFouce(){
        return editText.isFocused();
    }

    /**
     * @return 返回编辑框的信息
     */
    public String getEditTextInfo(){
        if (editText==null) {
            return null;
        }
        return  editText.getEditableText().toString();
    }

    public void setReadOnly()
    {
        editText.setCursorVisible(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

    public void setEditTextInfo(String text){
        if (editText==null) {
            return;
        }
        editText.setText(text);
    }

    public String getEditTextTagInfo(){
        if (editText==null) {
            return null;
        }
        if (editText.getTag()==null) {
            return null;
        }
        return  editText.getTag().toString();
    }

    public void setEditTextTagInfo(String text){
        if (editText==null) {
            return;
        }
        editText.setTag(text);
    }

    public void setEditTextTagInfo(String text,String option){
        if (editText==null) {
            return;
        }
        String labelStr= LattePreference.getCustomAppProfile(option);
        if(labelStr==null||"".equals(labelStr))
        {
            return;
        }
        JSONArray labels2=JSONArray.parseArray(labelStr);
        String[] tmp=new String [labels2.size()];
        for (int i=0;i<labels2.size();i++)
        {
            tmp[i]=labels2.getString(i);
        }
        final String[] labels=tmp;
        String valueStr=LattePreference.getCustomAppProfile(option+"_VAL");
        if(valueStr==null||"".equals(valueStr))
        {
            return;
        }
        final JSONArray values2=JSONArray.parseArray(valueStr);
        String[] tmp2=new String [values2.size()];
        for (int i=0;i<values2.size();i++)
        {
            if(values2.getString(i).equals(text))
            {
                editText.setTag(text);
                editText.setText(labels[i]);
                break;
            }
        }

    }

    public void clearText(){
        if (editText==null) {
            return;
        }
        editText.setText("");
        editText.setTag("");
    }

    /**
     * 设置线的颜色
     * @param normal : 正常的颜色值
     * @param select ： 选中的颜色值
     */
    public void setLineColorId(int normal , int select){
        if (normal>0) {
            normalColor = normal;
        }

        if (select>0) {
            selectColor = select ;
        }

        setLineBg(editText.isFocused());
    }

    public void setIFouceCall(IFouceCall iFouceCall) {
        this.iFouceCall = iFouceCall;
    }

    public TextView getErrorText() {
        return error;
    }


    /**
     * 设置错误提示背景为默认色
     */
    public void setErrorBgNormal(){
        getErrorText().setBackgroundColor(getResources().getColor(R.color.line_input_error_bg));
    }

    /**
     * 设置错误提示的背景色
     * @param colorId ：颜色的id值
     */
    public void setErrorBg(int colorId){
        getErrorText().setBackgroundColor(getResources().getColor(colorId));
    }

    public void setPopulWindow(final Context mContext,final String tblfieldArray,final RightAndLeftEditText rightAndLeftEditText,final String method,final String childTblfieldArray){
        setReadOnly();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EidtTextPopulWindow.create(mContext,editText).initListPopulWindow(tblfieldArray,rightAndLeftEditText,method,childTblfieldArray);
            }
        });
    }

    public void setPopulWindow(final Context mContext,final String tblfieldArray){
        setReadOnly();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EidtTextPopulWindow.create(mContext,editText).initListPopulWindow(tblfieldArray);
            }
        });
    }

    public void setDatePick(final BaseDelegate baseDelegate, final String initDateTime, final String dateType){
        setReadOnly();
        if (initDateTime!=null&&!"".equals(initDateTime))
            editText.setText(initDateTime);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("DATETIME".equals(dateType)) {
                    DateTimePickDialogUtil datePickDialogUtil = new DateTimePickDialogUtil(baseDelegate, initDateTime);
                    datePickDialogUtil.dateTimePicKDialog(editText);
                }else{
                    DatePickDialogUtil datePickDialogUtil = new DatePickDialogUtil(baseDelegate, initDateTime);
                    datePickDialogUtil.dateTimePicKDialog(editText);
                }
            }
        });
    }

    public void setCstUserDialog(final Context mContext){
        setReadOnly();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CstUserDialog.Builder builder=new CstUserDialog.Builder(mContext);
                builder.createSingleButtonDialog(new CstUserDialog.ConformListener() {
                    @Override
                    public void onConformClicked(String id, String name) {
                        editText.setText(name);
                        editText.setTag(id);


                    }
                }).show();
            }
        });
    }
}
