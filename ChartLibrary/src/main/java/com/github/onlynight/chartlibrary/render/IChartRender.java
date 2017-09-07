package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.chart.OnCrossPointClickListener;

/**
 * Created by lion on 2017/9/6.
 */

public interface IChartRender extends IChart {

    void onMeasure();

    void onDraw(Canvas canvas);

    void onDrawChart(Canvas canvas);

    double getYMaxValue();

    double getYMinValue();

    void setIsClipContainer(boolean isClipContainer);

    void setOnCrossPointClickListener(OnCrossPointClickListener onCrossPointClickListener);

    boolean isClipContainer();

    boolean isAutoZoomYAxis();

    void setIsAutoZoomYAxis(boolean isAutoZoomYAxis);

}
