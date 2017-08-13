package com.github.onlynight.chartlibrary.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */

public abstract class BaseChart<T extends BaseChartData> {

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public static final int BLANK = 5;

    protected int mLeft;
    protected int mTop;
    protected int mRight;
    protected int mBottom;

    private int mWidth;
    private int mHeight;

    protected Axis mXAxis;
    protected Axis mYAxis;
    protected Border mBorder;

    protected List<T> mDataList;
    private boolean mIsAlignYAxis = true;

    private double mWeight = 1d;

    public BaseChart() {
        this.mBorder = new Border();
        this.mYAxis = new Axis();
        this.mYAxis.setPosition(Axis.POSITION_LEFT);
        this.mXAxis = new Axis();
        this.mXAxis.setPosition(Axis.POSITION_BOTTOM);
        this.mDataList = new ArrayList<>();
    }

    public void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    private void drawBorder(Canvas canvas) {
        mGraphPaint.setColor(mBorder.getColor());
        mGraphPaint.setStrokeWidth(mBorder.getWidth());
        mGraphPaint.setStyle(Paint.Style.STROKE);

        float halfBorder = mBorder.getWidth() / 2;
//        canvas.drawRect(mLeft + halfBorder, mTop + halfBorder, mRight - halfBorder,
//                mBottom - halfBorder, mGraphPaint);

        if (mBorder.isHasTop()) {
            canvas.drawLine(mLeft + halfBorder, mTop + halfBorder,
                    mRight - halfBorder, mTop + halfBorder, mGraphPaint);
        }

        if (mBorder.isHasLeft()) {
            canvas.drawLine(mLeft + halfBorder, mTop + halfBorder,
                    mLeft + halfBorder, mBottom - halfBorder, mGraphPaint);
        }

        if (mBorder.isHasRight()) {
            canvas.drawLine(mRight - halfBorder, mTop + halfBorder,
                    mRight - halfBorder, mBottom - halfBorder, mGraphPaint);
        }

        if (mBorder.isHasBottom()) {
            canvas.drawLine(mLeft + halfBorder, mBottom - halfBorder,
                    mRight - halfBorder, mBottom - halfBorder, mGraphPaint);
        }
    }

    private void drawAxis(Canvas canvas) {
        String max = getYAxisTextMaxLength(mDataList);
        mGraphPaint.setTextSize(mYAxis.getTextSize());
        mGraphPaint.setStyle(Paint.Style.STROKE);
        float left = mGraphPaint.measureText(max);
//        mGraphPaint.setTextSize(mXAxis.getTextSize());
        float bottom = getFontHeight(mGraphPaint);
        drawXAxisLine(canvas);
        drawYAxisLine(canvas, left);
        drawYAxisText(canvas, left, bottom);
    }

    private void drawXAxisLine(Canvas canvas) {
        mGraphPaint.setTextSize(mXAxis.getTextSize());
        mGraphPaint.setColor(mXAxis.getColor());
        mGraphPaint.setStrokeWidth(mXAxis.getWidth());
        float fontHeight = getFontHeight(mGraphPaint);
        canvas.drawLine(mLeft + mBorder.getWidth(), mTop + fontHeight + BLANK + mBorder.getWidth(),
                mRight - mBorder.getWidth(), mTop + fontHeight + BLANK + mBorder.getWidth(), mGraphPaint);
    }

    private void drawYAxisLine(Canvas canvas, float leftWidth) {
        mGraphPaint.setStrokeWidth(mYAxis.getWidth());
        mGraphPaint.setColor(mYAxis.getColor());

        float x = leftWidth + BLANK * 2 + mBorder.getWidth();
        canvas.drawLine(mLeft + x, mTop + mBorder.getWidth(),
                mLeft + x, mTop + mHeight - mBorder.getWidth(), mGraphPaint);
    }

    private void drawYAxisText(Canvas canvas, float leftWidth, float fontHeight) {
        mTextPaint.setTextSize(mYAxis.getTextSize());
        mTextPaint.setColor(mYAxis.getTextColor());

        for (T entity : mDataList) {
            Object temp = entity.getData().get(0);
            if (temp instanceof BaseEntity) {
                canvas.drawText(((BaseEntity) temp).getyValue(), mLeft + BLANK + mBorder.getWidth(),
                        mTop + fontHeight + mBorder.getWidth(), mTextPaint);
            }
        }
    }

    public Border getBorder() {
        return mBorder;
    }

    public void setBorder(Border border) {
        this.mBorder = border;
    }

    public void setArea(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;

        this.mWidth = right - left;
        this.mHeight = bottom - top;
    }

    public Axis getxAxis() {
        return mXAxis;
    }

    public void setxAxis(Axis xAxis) {
        this.mXAxis = xAxis;
    }

    public Axis getyAxis() {
        return mYAxis;
    }

    public void setyAxis(Axis yAxis) {
        this.mYAxis = yAxis;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void addData(T data) {
        if (data != null) {
            mDataList.add(data);
        }
    }

    public void clearData() {
        mDataList.clear();
    }

    public boolean isAlignYAxis() {
        return mIsAlignYAxis;
    }

    public void setAlignYAxis(boolean alignYAxis) {
        mIsAlignYAxis = alignYAxis;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    private String getYAxisTextMaxLength(List<T> dataList) {
        if (dataList == null) {
            return "";
        }

        String max = "";
        for (T data : dataList) {
            String temp = getYAxisTextMaxLength(data);
            if (temp.length() > max.length()) {
                max = temp;
            }
        }

        return max;
    }

    private String getYAxisTextMaxLength(T chartData) {
        if (chartData == null) {
            return "";
        }

        String max = "";
        for (Object temp : chartData.getData()) {
            if (temp instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) temp;
                if (!TextUtils.isEmpty(entity.getyValue()) &&
                        entity.getyValue().length() > max.length()) {
                    max = entity.getyValue();
                }
            }
        }

        return max;
    }

    /**
     * @return get font height
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }
}
