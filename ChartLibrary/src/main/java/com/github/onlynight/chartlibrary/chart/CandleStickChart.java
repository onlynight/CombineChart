package com.github.onlynight.chartlibrary.chart;

import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.render.CandleStickChartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class CandleStickChart extends BaseChart<CandleStickChartData, CandleStickChartRender> {

    @Override
    protected CandleStickChartRender createChartRender() {
        return new CandleStickChartRender(this);
    }

    @Override
    protected void setExtremeValue(CandleStickChartData data) {
        if (data != null && data.getData() != null) {
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            for (CandleStickEntity entity : data.getData()) {
                if (entity.getHigh() > max) {
                    max = entity.getHigh();
                }

                if (entity.getLow() < min) {
                    min = entity.getLow();
                }
            }
            data.setYMin(min);
            data.setYMax(max);
        }
    }
}