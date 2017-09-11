package com.github.onlynight.chartlibrary.chart.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.chart.OnCrossPointClickListener;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.render.IChartRender;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */

public class BaseChart<T extends BaseChartData, Render extends IChartRender> implements IChart<T> {

    /**
     * common blank
     */
    public static final int BLANK = 5;

    /**
     * is show legend
     */
    private boolean mIsShowLegend = true;

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
        if (this.mRender != null) {
            this.mRender.setIsClipContainer(isClipContainer);
        }
    }

    public void setOnCrossPointClickListener(OnCrossPointClickListener onCrossPointClickListener) {
        mRender.setOnCrossPointClickListener(onCrossPointClickListener);
    }

    @Override
    public void setIsClipContainer(boolean isClipContainer) {
        if (mRender != null) {
            mRender.setIsClipContainer(isClipContainer);
        }
    }

    @Override
    public boolean isClipContainer() {
        return mRender != null && mRender.isClipContainer();
    }

    @Override
    public boolean isAutoZoomYAxis() {
        return mRender != null && mRender.isAutoZoomYAxis();
    }

    @Override
    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {
        if (mRender != null) {
            mRender.setIsAutoZoomYAxis(isAutoZoomYAxis);
        }
    }

    /**
     * on measure chart part proc
     */
    @Override
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
    @Override
    public void onDrawFrame(Canvas canvas) {
        if (mRender != null) {
            mRender.onDrawFrame(canvas);
        }
    }

    /**
     * draw chart data, exclude frame of the chart
     *
     * @param canvas
     */
    @Override
    public void onDrawChart(Canvas canvas) {
        if (mRender != null) {
            mRender.onDrawChart(canvas);
        }
    }

    private Render createChartRender() {
        try {
            Type type = getClass().getGenericSuperclass();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> clazz = (Class<?>) types[1];
            return (Render) clazz.getConstructor(BaseChart.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getMarginTextColor() {
        return mMarginTextColor;
    }

    @Override
    public void setMarginTextColor(int marginTextColor) {
        this.mMarginTextColor = marginTextColor;
    }

    @Override
    public int getLeft() {
        return mLeft;
    }

    @Override
    public void setLeft(int mLeft) {
        this.mLeft = mLeft;
    }

    @Override
    public int getTop() {
        return mTop;
    }

    @Override
    public void setTop(int mTop) {
        this.mTop = mTop;
    }

    @Override
    public int getRight() {
        return mRight;
    }

    @Override
    public void setRight(int mRight) {
        this.mRight = mRight;
    }

    @Override
    public int getBottom() {
        return mBottom;
    }

    @Override
    public void setBottom(int mBottom) {
        this.mBottom = mBottom;
    }

    @Override
    public double getWeight() {
        return mWeight;
    }

    @Override
    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    @Override
    public Border getBorder() {
        return mBorder;
    }

    @Override
    public void setBorder(Border border) {
        this.mBorder = border;
    }

    @Override
    public void setArea(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;

        this.mWidth = right - left;
        this.mHeight = bottom - top;
    }

    @Override
    public Axis getXAxis() {
        return mXAxis;
    }

    @Override
    public void setXAxis(Axis xAxis) {
        this.mXAxis = xAxis;
    }

    @Override
    public Axis getYAxis() {
        return mYAxis;
    }

    @Override
    public void setYAxis(Axis yAxis) {
        this.mYAxis = yAxis;
    }

    @Override
    public List<T> getDataList() {
        return mDataList;
    }

    @Override
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

    @Override
    public void clearData() {
        mDataList.clear();
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public float getMarginTextSize() {
        return mMarginTextSize;
    }

    @Override
    public void setMarginTextSize(float marginTextSize) {
        this.mMarginTextSize = marginTextSize;
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
    public float getXDelta() {
        if (mRender != null) {
            mRender.getXDelta();
        }
        return 0;
    }

    @Override
    public void setXDelta(float xDelta) {
        if (mRender != null) {
            mRender.setXDelta(xDelta);
        }
        resetExtremeValue();
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
    @Override
    public String getMaxYAxisScaleText() {
        return mMaxYAxisScaleText;
    }

    @Override
    public void setMaxYAxisScaleText(String maxYAxisScaleText) {
        this.mMaxYAxisScaleText = maxYAxisScaleText;
    }

    @Override
    public boolean isShowLegend() {
        return mIsShowLegend;
    }

    @Override
    public void setIsShowLegend(boolean isShowLegend) {
        this.mIsShowLegend = isShowLegend;
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

    @Override
    public boolean isCanZoomLessThanNormal() {
        return mRender.isCanZoomLessThanNormal();
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        mRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
    }

    @Override
    public PointF getCrossPoint() {
        return mRender.getCrossPoint();
    }

    @Override
    public int getCrossBorderColor() {
        return mRender.getCrossBorderColor();
    }

}
