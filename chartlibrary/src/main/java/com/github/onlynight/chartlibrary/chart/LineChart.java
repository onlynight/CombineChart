package com.github.onlynight.chartlibrary.chart;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.render.LineChartRender;

/**
 * Created by lion on 2017/8/11.
 * line chart
 */

public class LineChart extends BaseChart<LineChartData> {

    private LineChartRender mLineChartRender;

    public LineChart() {
        super();
        mLineChartRender = new LineChartRender(this);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mLineChartRender.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mLineChartRender.onDraw(canvas);
    }

    @Override
    public void setScale(float mScale) {
        super.setScale(mScale);
        mLineChartRender.setScale(mScale);
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        mLineChartRender.setxDelta(xDelta);
    }
}
