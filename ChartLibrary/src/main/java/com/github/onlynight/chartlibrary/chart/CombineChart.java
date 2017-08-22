package com.github.onlynight.chartlibrary.chart;

import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.render.CombineChartRender;

/**
 * Created by lion on 2017/8/22.
 */

public class CombineChart extends BaseChart<BaseChartData, CombineChartRender> {

    @Override
    protected CombineChartRender createChartRender() {
        return new CombineChartRender(this);
    }

    @Override
    public void setExtremeValue(BaseChartData data) {
        if (data != null && data.getData() != null) {
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            for (Object temp : data.getShowData()) {
                if (temp instanceof CandleStickEntity) {
                    CandleStickEntity entity = (CandleStickEntity) temp;
                    if (entity.getHigh() > max) {
                        max = entity.getHigh();
                    }

                    if (entity.getLow() < min) {
                        min = entity.getLow();
                    }

                    data.setYMin(min);
                    data.setYMax(max);
                }
            }
        }
    }
}
