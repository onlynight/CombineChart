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
    public void setExtremeValue(CandleStickChartData data) {
        if (data != null && data.getData() != null) {
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            int minIndex = 0, maxIndex = 0;
            for (int i = 0; i < data.getShowData().size(); i++) {
                CandleStickEntity entity = data.getShowData().get(i);
                if (entity.getHigh() > max) {
                    max = entity.getHigh();
                    maxIndex = i;
                }

                if (entity.getLow() < min) {
                    min = entity.getLow();
                    minIndex = i;
                }
            }
            data.setYMin(min);
            data.setYMax(max);
            data.setMinIndex(minIndex);
            data.setMaxIndex(maxIndex);
        }
    }
}
