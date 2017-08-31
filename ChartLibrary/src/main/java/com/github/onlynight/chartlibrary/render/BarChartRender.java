package com.github.onlynight.chartlibrary.render;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.render.part.BarPartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class BarChartRender extends BaseChartRender<BarChartData, BarPartRender> {

    public BarChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public BarPartRender createPartRender(BaseChart chart) {
        return new BarPartRender(chart);
    }

}
