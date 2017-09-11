package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.IChartOperate;

/**
 * Created by lion on 2017/9/6.
 */

public interface IPartRender extends IChartOperate {

    void onMeasure();

    void onDrawChart(Canvas canvas);

}
