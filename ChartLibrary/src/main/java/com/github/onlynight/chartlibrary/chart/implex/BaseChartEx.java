package com.github.onlynight.chartlibrary.chart.implex;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.render.IChartRender;

import java.util.List;

/**
 * Created by lion on 2017/9/11.
 */

public class BaseChartEx<T extends BaseChartData, Render extends IChartRender> implements IChart<T> {

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
     * is this chart align other chart y axis.
     */
    private boolean mIsAlignYAxis = true;

    /**
     * y axis max length text
     */
    private String mMaxYAxisScaleText = "";

    @Override
    public float getScale() {
        return 0;
    }

    @Override
    public void setScale(float scale) {

    }

    @Override
    public float getXDelta() {
        return 0;
    }

    @Override
    public void setXDelta(float xDelta) {

    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return false;
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {

    }

    @Override
    public void setCrossPoint(PointF crossPoint) {

    }

    @Override
    public PointF getCrossPoint() {
        return null;
    }

    @Override
    public void setCrossColor(int crossColor) {

    }

    @Override
    public int getCrossBorderColor() {
        return 0;
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {

    }

    @Override
    public void setCrossBorderColor(int color) {

    }

    @Override
    public void setWeight(double weight) {

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setArea(int left, int top, int right, int bottom) {

    }

    @Override
    public void onMeasure() {

    }

    @Override
    public void onDrawFrame(Canvas canvas) {

    }

    @Override
    public void onDrawChart(Canvas canvas) {

    }

    @Override
    public List<T> getDataList() {
        return null;
    }

    @Override
    public void clearData() {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Axis getXAxis() {
        return null;
    }

    @Override
    public Axis getYAxis() {
        return null;
    }

    @Override
    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {

    }

    @Override
    public boolean isAutoZoomYAxis() {
        return false;
    }

    @Override
    public float getMarginTextSize() {
        return 0;
    }

    @Override
    public void setMarginTextSize(float marginTextSize) {

    }

    @Override
    public String getMaxYAxisScaleText() {
        return null;
    }

    @Override
    public void setMaxYAxisScaleText(String maxYAxisScaleText) {

    }

    @Override
    public void setIsClipContainer(boolean clipContainer) {

    }

    @Override
    public boolean isClipContainer() {
        return false;
    }

    @Override
    public boolean isShowLegend() {
        return false;
    }

    @Override
    public void setIsShowLegend(boolean isShowLegend) {

    }

    @Override
    public int getMarginTextColor() {
        return 0;
    }

    @Override
    public void setMarginTextColor(int marginTextColor) {

    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public void setLeft(int mLeft) {

    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public void setTop(int mTop) {

    }

    @Override
    public int getRight() {
        return 0;
    }

    @Override
    public void setRight(int mRight) {

    }

    @Override
    public int getBottom() {
        return 0;
    }

    @Override
    public void setBottom(int mBottom) {

    }

    @Override
    public Border getBorder() {
        return null;
    }

    @Override
    public void setBorder(Border border) {

    }

    @Override
    public void setXAxis(Axis xAxis) {

    }

    @Override
    public void setYAxis(Axis yAxis) {

    }

    @Override
    public void addData(T data) {

    }
}
