package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.IChart;

/**
 * Created by lion on 2017/9/6.
 */

public interface IPartRender extends IChart {

    void onMeasure();

    void onDrawChart(Canvas canvas);

}
