package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.BaseChart;

/**
 * Created by lion on 2017/8/29.
 */

public class CombinePartRender extends BasePartRender {

    private LinePartRender mLinePartRender;
    private BarPartRender mBarPartRender;
    private CandleStickPartRender mCandleStickPartRender;

    public CombinePartRender(BaseChart chart) {
        super(chart);
        this.mLinePartRender = new LinePartRender(chart);
        this.mBarPartRender = new BarPartRender(chart);
        this.mCandleStickPartRender = new CandleStickPartRender(chart);
    }

    @Override
    public void onMeasure() {
        mLinePartRender.onMeasure();
        mBarPartRender.onMeasure();
        mCandleStickPartRender.onMeasure();
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        mBarPartRender.onDrawChart(canvas);
        mCandleStickPartRender.onDrawChart(canvas);
        mLinePartRender.onDrawChart(canvas);

        drawCross(canvas);
    }

    @Override
    public float getScale() {
        return mCandleStickPartRender.getScale();
    }

    @Override
    public void setScale(float scale) {
        mLinePartRender.setScale(scale);
        mBarPartRender.setScale(scale);
        mCandleStickPartRender.setScale(scale);
        super.setScale(scale);
    }

    @Override
    public float getxDelta() {
        return mCandleStickPartRender.getxDelta();
    }

    @Override
    public void setxDelta(float xDelta) {
        mBarPartRender.setxDelta(xDelta);
        mLinePartRender.setxDelta(xDelta);
        mCandleStickPartRender.setxDelta(xDelta);
        super.setxDelta(xDelta);
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return mCandleStickPartRender.isCanZoomLessThanNormal();
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        mBarPartRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
        mLinePartRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
        mCandleStickPartRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
    }

}
