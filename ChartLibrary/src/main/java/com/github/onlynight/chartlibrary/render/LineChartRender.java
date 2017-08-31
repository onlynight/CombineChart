package com.github.onlynight.chartlibrary.render;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.render.part.LinePartRender;

/**
 * Created by lion on 2017/8/10.
 */

public class LineChartRender extends BaseChartRender<LineChartData, LinePartRender> {

    public LineChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public LinePartRender createPartRender(BaseChart chart) {
        return new LinePartRender(chart);
    }

}
