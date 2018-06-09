package net.bhtech.lygmanager.ui.tag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.bhtech.lygmanager.isecuritys.R;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class RightAndLeftTextView extends LinearLayout {

    private LinearLayout rootView ;
    /** 内容 */
    private TextView rightTextView;

    /** 标题  */
    private TextView leftTextView;
    /** 标题  */
    private TextView colon;
    private View raltextviewline;

    @Override
    public LinearLayout getRootView() {
        return rootView;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public void setRightTextView(TextView rightTextView) {
        this.rightTextView = rightTextView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public void setLeftTextView(TextView leftTextView) {
        this.leftTextView = leftTextView;
    }

    public String getRightText() {
        return rightTextView.getText().toString();
    }

    public void setRightText(String rightText) {
        this.rightTextView.setText(rightText);
    }

    public String getLeftText() {
        return leftTextView.getText().toString();
    }

    public void setLeftText(String leftText) {
        this.leftTextView.setText(leftText);
    }



    public RightAndLeftTextView(Context context) {
        super(context);
        init(context, null);
    }

    public RightAndLeftTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("NewApi")
    public RightAndLeftTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        // 留意，父组件是this,不是空
        View allView = inflate(context, R.layout.tag_rightandleftetextview, this);
        rootView = (LinearLayout)allView.findViewById(R.id.line_top);
        leftTextView = (TextView) allView.findViewById(R.id.lefttext);
        rightTextView = (TextView) allView.findViewById(R.id.righttext);
        colon = (TextView) allView.findViewById(R.id.colon);
        raltextviewline = allView.findViewById(R.id.raltextviewline);
        initAttribute(attrs);
    }


    /**
     * @param attrs : 配置属性
     * 解析属性
     */
    private void initAttribute(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.RightAndLeftTextView);
            String left_text = a.getString(R.styleable.RightAndLeftTextView_left_text);
            int left_size = a.getDimensionPixelSize(R.styleable.RightAndLeftTextView_left_size, 0);
            int left_color = a.getColor(R.styleable.RightAndLeftTextView_left_color, 0);
            boolean wrapcontent = a.getBoolean(R.styleable.RightAndLeftTextView_wrapcontent,false);

            if (left_size > 0) {
                leftTextView.setTextSize(left_size);
            }

            if (left_color > 0) {
                leftTextView.setTextColor(left_color);
            }

            if (!TextUtils.isEmpty(left_text)) {
                leftTextView.setText(left_text);
            }else {
                colon.setVisibility(GONE);
                leftTextView.setVisibility(GONE);
                rightTextView.getPaint().setFakeBoldText(true);
            }

            String right_text = a.getString(R.styleable.RightAndLeftTextView_right_text);
            int right_size = a.getDimensionPixelSize(R.styleable.RightAndLeftTextView_right_size, 0);
            int right_color = a.getColor(R.styleable.RightAndLeftTextView_right_color, 0);

            if (right_size > 0) {
                rightTextView.setTextSize(right_size);
            }

            if (right_color > 0) {
                rightTextView.setTextColor(right_color);
            }

            if (!TextUtils.isEmpty(right_text)) {
                rightTextView.setText(right_text);
            }

            if (wrapcontent) {
                ViewGroup.LayoutParams params = leftTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                raltextviewline.setVisibility(GONE);
            }
        }
    }


}
