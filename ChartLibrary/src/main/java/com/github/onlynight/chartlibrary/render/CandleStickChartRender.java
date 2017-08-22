package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.render.part.CandleStickPartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class CandleStickChartRender extends BaseChartRender<CandleStickChartData> {

    private CandleStickPartRender mCandleStickPartRender;

    public CandleStickChartRender(BaseChart chart) {
        super(chart);
        this.mCandleStickPartRender = new CandleStickPartRender(chart);
    }

    @Override
    public void onMeasure() {
        super.onMeasure();
        mCandleStickPartRender.onMeasure();
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        super.onDrawChart(canvas);
        mCandleStickPartRender.onDrawChart(canvas);
    }

    @Override
    public float getScale() {
        return mCandleStickPartRender.getScale();
    }

    @Override
    public void setScale(float mScale) {
        super.setScale(mScale);
        mCandleStickPartRender.setScale(mScale);
    }

    @Override
    public float getxDelta() {
        return mCandleStickPartRender.getxDelta();
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        mCandleStickPartRender.setxDelta(xDelta);
    }

}
