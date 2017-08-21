package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */

public class LineChartData extends BaseChartData<LineEntity, LineChartDataConfig> {
    public LineChartData() {
    }

    public LineChartData(List<LineEntity> data) {
        super(data);
    }

    public LineChartData(LineChartDataConfig config) {
        super(config);
    }
}
