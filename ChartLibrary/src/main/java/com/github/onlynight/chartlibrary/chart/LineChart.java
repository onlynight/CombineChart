package com.github.onlynight.chartlibrary.chart;

import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.render.LineChartRender;

/**
 * Created by lion on 2017/8/11.
 * line chart
 */

public class LineChart extends BaseChart<LineChartData, LineChartRender> {

    @Override
    protected LineChartRender createChartRender() {
        return new LineChartRender(this);
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return mRender.isCanZoomLessThanNormal();
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        mRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
    }

}
