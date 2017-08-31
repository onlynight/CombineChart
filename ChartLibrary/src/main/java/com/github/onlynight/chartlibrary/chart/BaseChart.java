package com.github.onlynight.chartlibrary.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.operate.IChartInterface;
import com.github.onlynight.chartlibrary.render.BaseChartRender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */

public abstract class BaseChart<T extends BaseChartData, Render extends BaseChartRender> implements
        IChartInterface, BaseChartRender.OnResetExtremeValueListener {

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
    private float mMarginTextSize = 20;

    /**
     * chart top or bottom margin text color.
     */
    private int mMarginTextColor = Color.BLACK;

    /**
     * chart data list
     */
    protected List<T> mDataList;

    protected Render mRender;

    /**
     * is this chart align other chart y axis.
     */
    private boolean mIsAlignYAxis = true;

    /**
     * y axis max length text
     */
    private String mMaxYAxisScaleText = "";

    public BaseChart() {
        this(false);
    }

    public BaseChart(boolean isClipContainer) {
        this.mBorder = new Border();
        this.mYAxis = new Axis();
        this.mYAxis.setPosition(Axis.POSITION_LEFT);
        this.mXAxis = new Axis();
        this.mXAxis.setPosition(Axis.POSITION_BOTTOM);
        this.mDataList = new ArrayList<>();
        this.mRender = createChartRender();
        this.mRender.setIsClipContainer(isClipContainer);
    }

    public void setIsClipContainer(boolean isClipContainer) {
        if (mRender != null) {
            mRender.setIsClipContainer(isClipContainer);
        }
    }

    public boolean isClipContainer() {
        return mRender != null && mRender.isClipContainer();
    }

    public boolean isAutoZoomYAxis() {
        return mRender != null && mRender.isAutoZoomYAxis();
    }

    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {
        if (mRender != null) {
            mRender.setIsAutoZoomYAxis(isAutoZoomYAxis);
        }
    }

    /**
     * on measure chart part proc
     */
    public void onMeasure() {
        if (mRender != null) {
            mRender.onMeasure();
        }
    }

    /**
     * on draw chart part proc
     *
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        if (mRender != null) {
            mRender.onDraw(canvas);
        }
    }

    /**
     * draw chart data, exclude frame of the chart
     *
     * @param canvas
     */
    public void onDrawChart(Canvas canvas) {
        if (mRender != null) {
            mRender.onDrawChart(canvas);
        }
    }

    /**
     * create chart render
     *
     * @return
     */
    protected abstract Render createChartRender();

    public int getMarginTextColor() {
        return mMarginTextColor;
    }

    public void setMarginTextColor(int marginTextColor) {
        this.mMarginTextColor = marginTextColor;
    }

    public int getLeft() {
        return mLeft;
    }

    public void setLeft(int mLeft) {
        this.mLeft = mLeft;
    }

    public int getTop() {
        return mTop;
    }

    public void setTop(int mTop) {
        this.mTop = mTop;
    }

    public int getRight() {
        return mRight;
    }

    public void setRight(int mRight) {
        this.mRight = mRight;
    }

    public int getBottom() {
        return mBottom;
    }

    public void setBottom(int mBottom) {
        this.mBottom = mBottom;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
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
            setExtremeValue(data);
        }
    }

    public void setExtremeValue(T data) {
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        int maxIndex = 0, minIndex = 0;
        for (int i = 0; i < data.getShowData().size(); i++) {
            Object obj = data.getShowData().get(i);
            if (obj instanceof BaseEntity) {
                double y = ((BaseEntity) obj).getY();
                if (max < y) {
                    max = y;
                    maxIndex = i;
                }

                if (min > y) {
                    min = y;
                    minIndex = i;
                }
            }
        }
        data.setYMax(max);
        data.setYMin(min);
        data.setMinIndex(minIndex);
        data.setMaxIndex(maxIndex);
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

    public float getMarginTextSize() {
        return mMarginTextSize;
    }

    public void setMarginTextSize(float marginTextSize) {
        this.mMarginTextSize = marginTextSize;
    }

    public static int getBLANK() {
        return BLANK;
    }

    @Override
    public float getScale() {
        if (mRender != null) {
            mRender.getScale();
        }
        return 1;
    }

    @Override
    public void setScale(float scale) {
        if (mRender != null) {
            mRender.setScale(scale);
        }
        resetExtremeValue();
    }

    @Override
    public float getxDelta() {
        if (mRender != null) {
            mRender.getxDelta();
        }
        return 0;
    }

    @Override
    public void setxDelta(float xDelta) {
        if (mRender != null) {
            mRender.setxDelta(xDelta);
        }
        resetExtremeValue();
    }

    @Override
    public void onResetExtremeValue() {
    }

    private void resetExtremeValue() {
//        for (T data : mDataList) {
//            setExtremeValue(data);
//        }

        try {
            setExtremeValue(mDataList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * generate static y axis scale text
     *
     * @return if is null or is "" then it will measure the max text width
     */
    public String generateMaxLengthYAxisScaleText() {
        return mMaxYAxisScaleText;
    }

    public void setMaxYAxisScaleText(String maxYAxisScaleText) {
        this.mMaxYAxisScaleText = maxYAxisScaleText;
    }

    @Override
    public void setCrossPoint(PointF crossPoint) {
        mRender.setCrossPoint(crossPoint);
    }

    @Override
    public void setCrossColor(int crossColor) {
        mRender.setCrossColor(crossColor);
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        mRender.setCrossLineWidth(crossLineWidth);
    }

    @Override
    public void setCrossBorderColor(int color) {
        mRender.setCrossBorderColor(color);
    }
}
