package com.github.onlynight.chartlibrary.render.implex.part;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.render.part.IPartRender;

/**
 * Created by lion on 2017/9/11.
 */

public class BasePartRenderEx implements IPartRender {

    static final int DEFAULT_BAR_WIDTH = 2;

    IChart mChart;

    Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float mScale = 1f;
    float mXDelta = 0;

    private PointF mCrossPoint;
    private int mCrossColor = Color.GRAY;
    private float mCrossLineWidth = 2f;
    private int mCrossBorderColor = Color.GRAY;

    protected boolean mIsCanZoomLessThanNormal = true;

    BasePartRenderEx(IChart chart) {
        this.mChart = chart;
        this.mCrossPoint = new PointF(-1, -1);
    }

    @Override
    public float getScale() {
        return mScale;
    }

    @Override
    public void setScale(float scale) {
        this.mScale = scale;
    }

    @Override
    public float getXDelta() {
        return mXDelta;
    }

    @Override
    public void setXDelta(float xDelta) {
        this.mXDelta = xDelta;
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return mIsCanZoomLessThanNormal;
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        this.mIsCanZoomLessThanNormal = canZoomLessThanNormal;
    }

    @Override
    public void setCrossPoint(PointF crossPoint) {
        this.mCrossPoint = crossPoint;
    }

    @Override
    public PointF getCrossPoint() {
        return mCrossPoint;
    }

    @Override
    public void setCrossColor(int crossColor) {
        this.mCrossColor = crossColor;
    }

    @Override
    public int getCrossBorderColor() {
        return mCrossBorderColor;
    }

    @Override
    public void setCrossBorderColor(int color) {
        mCrossBorderColor = color;
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        this.mCrossLineWidth = crossLineWidth;
    }

    @Override
    public void onMeasure() {
    }

    @Override
    public void onDrawChart(Canvas canvas) {
    }

}
