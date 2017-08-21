package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public class CandleStickChartData extends BaseChartData<CandleStickEntity, CandleStickChartDataConfig> {
    public CandleStickChartData() {
    }

    public CandleStickChartData(List<CandleStickEntity> data) {
        super(data);
    }

    public CandleStickChartData(CandleStickChartDataConfig config) {
        super(config);
    }
}
