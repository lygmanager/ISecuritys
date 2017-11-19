package net.bhtech.lygmanager.ui.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import net.bhtech.lygmanager.isecuritys.R;

/**
 * Created by zhangxinbiao on 2017/11/18.
 */

public class RightAndLeftTextView extends android.support.v7.widget.AppCompatTextView {

    /**
     * PS：TextView有默认 TextSize，setXxxSize时会有视差，所以setXxxText前设置为左右之中最大的 TextSize
     *      -- xml中：android:textSize="xxsp"
     *      -- 代码中：setTextSize(TypedValue.COMPLEX_UNIT_SP, xx);
     */

    // 文本
    private String mLeftText;
    private String mRightText;
    // 文本颜色
    private int mLeftTextColor;
    private int mRightTextColor;
    // 文本大小
    private float mLeftTextSize;
    private float mRightTextSize;
    // Paint
    private TextPaint mPaint;
    // 宽高
    private int mWidth;
    private int mHalfWidth;
    private int mRightTextWidth;
    private int mLeftTextWidth;
    private int mRightTextX;

    public RightAndLeftTextView(Context context) {
        this(context, null);
    }

    public RightAndLeftTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightAndLeftTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RightAndLeftTextView, defStyleAttr, 0);
        mLeftText = a.getString(R.styleable.RightAndLeftTextView_left_text);
        mLeftTextColor = a.getColor(R.styleable.RightAndLeftTextView_left_color, Color.BLACK);
        mLeftTextSize = a.getDimension(R.styleable.RightAndLeftTextView_left_size, 40);
        mRightText = a.getString(R.styleable.RightAndLeftTextView_right_text);
        mRightTextColor = a.getColor(R.styleable.RightAndLeftTextView_right_color, Color.BLACK);
        mRightTextSize = a.getDimension(R.styleable.RightAndLeftTextView_right_size, 40);
        // 注意回收
        a.recycle();

        mPaint = new TextPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHalfWidth = 2*mWidth / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRightText(canvas);
        drawLeftText(canvas);

        // 设置默认文本大小
        if (mLeftTextSize < mRightTextSize) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
        }
        // 设置行数
        if (mLeftTextWidth >= mHalfWidth || mRightTextWidth >= mHalfWidth) {
            if (mLeftTextWidth < mRightTextWidth) {
                if ((mRightTextWidth % mHalfWidth) > 0) {
                    setLines(mRightTextWidth / mHalfWidth + 1);
                } else {
                    setLines(mRightTextWidth / mHalfWidth);
                }
            } else if (mRightTextWidth < mHalfWidth) {
                if ((mLeftTextWidth % (mRightTextX)) > 0) {
                    setLines(mLeftTextWidth / mRightTextX + 1);
                } else {
                    setLines(mLeftTextWidth / mRightTextX);
                }
            } else {
                if ((mLeftTextWidth % mHalfWidth) > 0) {
                    setLines(mLeftTextWidth / mHalfWidth + 1);
                } else {
                    setLines(mLeftTextWidth / mHalfWidth);
                }
            }
        }
    }

    /**
     * 左边 text
     */
    private void drawLeftText(Canvas canvas) {
        mPaint.setTextSize(mLeftTextSize);
        mPaint.setColor(mLeftTextColor);
        mLeftTextWidth = (int) mPaint.measureText(mLeftText);
        // 绘制文字，不能换行
        // canvas.drawText(mLeftText, 0, mPaint.getTextSize(), mPaint);
        // 绘制文字，能换行
        canvas.save();
        canvas.translate(0, 0);
        StaticLayout staticLayout = new StaticLayout(mLeftText, mPaint, mRightTextX, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    /**
     * 右边 text
     */
    private void drawRightText(Canvas canvas) {
        mPaint.setTextSize(mRightTextSize);
        mPaint.setColor(mRightTextColor);
        mRightTextWidth = (int) mPaint.measureText(mRightText);
        // 绘制文字，不能换行
        // canvas.drawText(mRightText, mWidth - mRightTextWidth, mHeight, mPaint);
        // 绘制文字，能换行
        canvas.save();
        StaticLayout staticLayout;
        // 左右两边的 text长度如果超过一半 mWidth就换行
        if (mRightTextWidth >= mHalfWidth) {
            mRightTextX = mHalfWidth;
            canvas.translate(mRightTextX, 0);
            staticLayout = new StaticLayout(mRightText, mPaint, mHalfWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        } else {
            mRightTextX = mWidth - mRightTextWidth;
            canvas.translate(mRightTextX, 0);
            staticLayout = new StaticLayout(mRightText, mPaint, mRightTextWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        }
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public String getLeftText() {
        return mLeftText;
    }

    public void setLeftText(String leftText) {
        mLeftText = leftText;
        invalidate();
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightText(String rightText) {
        mRightText = rightText;
        invalidate();
    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        mLeftTextColor = leftTextColor;
        invalidate();
    }

    public int getRightTextColor() {
        return mRightTextColor;
    }

    public void setRightTextColor(int rightTextColor) {
        mRightTextColor = rightTextColor;
        invalidate();
    }

    public float getLeftTextSize() {
        return mLeftTextSize;
    }

    public void setLeftTextSize(float leftTextSize) {
        mLeftTextSize = leftTextSize;
        invalidate();
    }

    public float getRightTextSize() {
        return mRightTextSize;
    }

    public void setRightTextSize(float rightTextSize) {
        mRightTextSize = rightTextSize;
        invalidate();
    }
}
