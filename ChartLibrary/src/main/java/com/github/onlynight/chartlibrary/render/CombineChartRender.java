package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.render.part.BarPartRender;
import com.github.onlynight.chartlibrary.render.part.CandleStickPartRender;
import com.github.onlynight.chartlibrary.render.part.LinePartRender;

/**
 * Created by lion on 2017/8/22.
 */

public class CombineChartRender extends BaseChartRender<BaseChartData> {

    private LinePartRender mLinePartRender;
    private BarPartRender mBarPartRender;
    private CandleStickPartRender mCandleStickPartRender;

    public CombineChartRender(BaseChart chart) {
        super(chart);
        this.mLinePartRender = new LinePartRender(chart);
        this.mBarPartRender = new BarPartRender(chart);
        this.mCandleStickPartRender = new CandleStickPartRender(chart);
    }

    @Override
    public void onMeasure() {
        super.onMeasure();
        mLinePartRender.onMeasure();
        mBarPartRender.onMeasure();
        mCandleStickPartRender.onMeasure();
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        super.onDrawChart(canvas);
        mBarPartRender.onDrawChart(canvas);
        mCandleStickPartRender.onDrawChart(canvas);
        mLinePartRender.onDrawChart(canvas);
    }

    @Override
    public float getScale() {
        return mCandleStickPartRender.getScale();
    }

    @Override
    public void setScale(float mScale) {
        super.setScale(mScale);
        mLinePartRender.setScale(mScale);
        mBarPartRender.setScale(mScale);
        mCandleStickPartRender.setScale(mScale);
    }

    @Override
    public float getxDelta() {
        return mCandleStickPartRender.getxDelta();
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        mBarPartRender.setxDelta(xDelta);
        mLinePartRender.setxDelta(xDelta);
        mCandleStickPartRender.setxDelta(xDelta);
    }

}
