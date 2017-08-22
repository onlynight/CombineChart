package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.render.part.LinePartRender;

/**
 * Created by lion on 2017/8/10.
 */

public class LineChartRender extends BaseChartRender<LineChartData> {

    private LinePartRender mLinePartRender;

    public LineChartRender(BaseChart chart) {
        super(chart);
        this.mLinePartRender = new LinePartRender(chart);
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        super.onDrawChart(canvas);
        mLinePartRender.onDrawChart(canvas);
    }

    @Override
    public void setScale(float mScale) {
        super.setScale(mScale);
        mLinePartRender.setScale(mScale);
    }

    @Override
    public float getScale() {
        return mLinePartRender.getScale();
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        mLinePartRender.setxDelta(xDelta);
    }

    @Override
    public float getxDelta() {
        return mLinePartRender.getxDelta();
    }
}
