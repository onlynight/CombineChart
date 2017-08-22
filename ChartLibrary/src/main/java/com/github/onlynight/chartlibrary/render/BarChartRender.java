package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.render.part.BarPartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class BarChartRender extends BaseChartRender<BarChartData> {

    private BarPartRender mBarPartRender;

    public BarChartRender(BaseChart chart) {
        super(chart);
        this.mBarPartRender = new BarPartRender(chart);
    }

    @Override
    public void onMeasure() {
        super.onMeasure();
        mBarPartRender.onMeasure();
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        super.onDrawChart(canvas);
        mBarPartRender.onDrawChart(canvas);
    }

    @Override
    public float getScale() {
        return mBarPartRender.getScale();
    }

    @Override
    public void setScale(float mScale) {
        super.setScale(mScale);
        mBarPartRender.setScale(mScale);
    }

    @Override
    public float getxDelta() {
        return mBarPartRender.getxDelta();
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        mBarPartRender.setxDelta(xDelta);
    }

}
