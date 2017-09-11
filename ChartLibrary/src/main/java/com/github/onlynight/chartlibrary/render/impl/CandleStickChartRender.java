package com.github.onlynight.chartlibrary.render.impl;

import com.github.onlynight.chartlibrary.chart.impl.BaseChart;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.render.impl.part.CandleStickPartRender;

/**
 * Created by lion on 2017/9/7.
 */

public class CandleStickChartRender extends BaseChartRender<CandleStickChartData, CandleStickPartRender> {

    public CandleStickChartRender(BaseChart chart) {
        super(chart);
    }

}
