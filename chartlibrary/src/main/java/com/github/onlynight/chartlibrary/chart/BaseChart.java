package com.github.onlynight.chartlibrary.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
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

    /**
     * graph paint
     */
    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * text paint
     */
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * common blank
     */
    public static final int BLANK = 5;

    /**
     * chart area
     */
    protected int mLeft;
    protected int mTop;
    protected int mRight;
    protected int mBottom;

    /**
     * chart size
     */
    private int mWidth;
    private int mHeight;

    /**
     * axis
     */
    protected Axis mXAxis;
    protected Axis mYAxis;

    /**
     * chart border
     */
    protected Border mBorder;

    /**
     * chart weight
     */
    private double mWeight = 1d;

    /**
     * chart top or bottom margin text size,
     * <p>
     * it will use for top ro bottom margin.
     */
    private double mMarginTextSize = 20;

    /**
     * chart data list
     */
    protected List<T> mDataList;

    /**
     * is this chart align other chart y axis.
     */
    private boolean mIsAlignYAxis = true;

    public BaseChart() {
        this.mBorder = new Border();
        this.mYAxis = new Axis();
        this.mYAxis.setPosition(Axis.POSITION_LEFT);
        this.mXAxis = new Axis();
        this.mXAxis.setPosition(Axis.POSITION_BOTTOM);
        this.mDataList = new ArrayList<>();
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    /**
     * on measure chart part proc
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureBorder();
        measureAxis();
    }

    protected void measureBorder() {
        float halfBorder = mBorder.getWidth() / 2;
        PointF topLeft = new PointF(mLeft + halfBorder, mTop + halfBorder);
        PointF bottomRight = new PointF(mRight - halfBorder, mBottom - halfBorder);
        mBorder.setTopLeft(topLeft);
        mBorder.setBottomRight(bottomRight);
    }

    protected void measureAxis() {
        measureYAxis();
        measureXAxis();
    }

    protected void measureYAxis() {
        mTextPaint.setTextSize(mYAxis.getTextSize());
        mGraphPaint.setStrokeWidth(mYAxis.getWidth());

        String max = getYAxisTextMaxLength(mDataList);
        float scaleMaxWidth = mTextPaint.measureText(max);

        float x = 0;

        switch (mYAxis.getPosition()) {
            case Axis.POSITION_RIGHT:
                x = mRight - (scaleMaxWidth + BLANK * 2);
                mXAxis.getStartPos().x = 0;
                mXAxis.getEndPos().x = x;
                break;
            case Axis.POSITION_LEFT:
            default:
                x = scaleMaxWidth + BLANK * 2;
                mXAxis.getStartPos().x = x;
                mXAxis.getEndPos().x = mRight;
                break;
        }

        mYAxis.getStartPos().x = x;
        mYAxis.getEndPos().x = x;
    }

    protected void measureXAxis() {
        mTextPaint.setTextSize(mXAxis.getTextSize());
        mGraphPaint.setStrokeWidth(mXAxis.getWidth());

        float fontHeight = getFontHeight(mTextPaint);

        float y = 0;

        switch (mXAxis.getPosition()) {
            case Axis.POSITION_BOTTOM:
                y = mBottom - (fontHeight * BLANK * 2);
                mYAxis.getStartPos().y = mTop + mBorder.getWidth() / 2;
                mYAxis.getEndPos().y = y;
            default:
                break;
            case Axis.POSITION_TOP:
                y = mTop + fontHeight * BLANK * 2;
                mYAxis.getStartPos().y = y;
                mYAxis.getEndPos().y = mBottom - mBorder.getWidth() / 2;
                break;
        }

        mXAxis.getStartPos().y = y;
        mXAxis.getEndPos().y = y;
    }

    /**
     * on draw chart part proc
     *
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
//        drawAxis(canvas);
    }

    private void drawBorder(Canvas canvas) {
        mGraphPaint.setColor(mBorder.getColor());
        mGraphPaint.setStrokeWidth(mBorder.getWidth());
        mGraphPaint.setStyle(Paint.Style.STROKE);

        if (mBorder.isHasLeft()) {
            canvas.drawLine(mBorder.getTopLeft().x, mBorder.getTopLeft().y,
                    mBorder.getTopLeft().x, mBorder.getBottomRight().y, mGraphPaint);
        }

        if (mBorder.isHasTop()) {
            canvas.drawLine(mBorder.getTopLeft().x, mBorder.getTopLeft().y,
                    mBorder.getBottomRight().x, mBorder.getTopLeft().y, mGraphPaint);
        }

        if (mBorder.isHasRight()) {
            canvas.drawLine(mBorder.getBottomRight().x, mBorder.getTopLeft().y,
                    mBorder.getBottomRight().x, mBorder.getBottomRight().y, mGraphPaint);
        }

        if (mBorder.isHasBottom()) {
            canvas.drawLine(mBorder.getTopLeft().x, mBorder.getBottomRight().y,
                    mBorder.getBottomRight().x, mBorder.getBottomRight().y, mGraphPaint);
        }
    }

    private void drawAxis(Canvas canvas) {
    }

//    private void drawAxis(Canvas canvas) {
//        String max = getYAxisTextMaxLength(mDataList);
//        mGraphPaint.setTextSize(mYAxis.getTextSize());
//        mGraphPaint.setStyle(Paint.Style.STROKE);
//        float left = mGraphPaint.measureText(max);
////        mGraphPaint.setTextSize(mXAxis.getTextSize());
//        float bottom = getFontHeight(mGraphPaint);
//        drawXAxisLine(canvas);
//        drawYAxisLine(canvas, left);
//        drawYAxisText(canvas, left, bottom);
//    }

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

    public double getMarginTextSize() {
        return mMarginTextSize;
    }

    public void setMarginTextSize(double marginTextSize) {
        this.mMarginTextSize = marginTextSize;
    }
}
