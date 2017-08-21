package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.BarChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */

public class BarChartData extends BaseChartData<BarEntity, BarChartDataConfig> {

    public BarChartData() {
    }

    public BarChartData(List<BarEntity> data) {
        super(data);
    }

    public BarChartData(BarChartDataConfig config) {
        super(config);
    }

}
