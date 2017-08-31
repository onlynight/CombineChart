package com.github.onlynight.chartlibrary.render;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.render.part.BasePartRender;
import com.github.onlynight.chartlibrary.render.part.CandleStickPartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class CandleStickChartRender extends BaseChartRender<CandleStickChartData, CandleStickPartRender> {

    public CandleStickChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public CandleStickPartRender createPartRender(BaseChart chart) {
        return new CandleStickPartRender(chart);
    }

}
