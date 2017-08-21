package com.github.onlynight.chartlibrary.chart;

import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.render.BarChartRender;

/**
 * Created by lion on 2017/8/21.
 */

public class BarChart extends BaseChart<BarChartData, BarChartRender> {

    @Override
    protected BarChartRender createChartRender() {
        return new BarChartRender(this);
    }

    @Override
    public void setxDelta(float xDelta) {
        super.setxDelta(xDelta);
        resetExtremeValue();
    }

    private void resetExtremeValue() {
        for (BarChartData data : mDataList) {
            setExtremeValue(data);
        }
    }
}
