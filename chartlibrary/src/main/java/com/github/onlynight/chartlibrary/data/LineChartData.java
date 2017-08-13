package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

/**
 * Created by lion on 2017/8/11.
 */

public class LineChartData extends BaseChartData<LineEntity, LineChartDataConfig> {

    public LineChartData(LineChartDataConfig config) {
        this.mConfig = config;
    }

}
