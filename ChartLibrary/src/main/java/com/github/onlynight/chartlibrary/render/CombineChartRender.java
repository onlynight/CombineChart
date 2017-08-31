package com.github.onlynight.chartlibrary.render;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.render.part.CombinePartRender;

/**
 * Created by lion on 2017/8/22.
 */

public class CombineChartRender extends BaseChartRender<BaseChartData, CombinePartRender> {

    public CombineChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public CombinePartRender createPartRender(BaseChart chart) {
        return new CombinePartRender(chart);
    }

}
