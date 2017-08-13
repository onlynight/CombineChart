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
        mLineChartRender = new LineChartRender();
    }

    @Override
    public void setArea(int left, int top, int right, int bottom) {
        super.setArea(left, top, right, bottom);
        float halfBorder = mBorder.getWidth() / 2;
        mLineChartRender.setArea((int) (left + halfBorder), (int) (top + halfBorder),
                (int) (right - halfBorder), (int) (bottom - halfBorder));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLineChartRender.onDraw(canvas);
    }

}
