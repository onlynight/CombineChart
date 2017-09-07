package com.github.onlynight.chartlibrary.chart;

import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

import java.util.List;

/**
 * Created by lion on 2017/9/7.
 */

public interface OnCrossPointClickListener {

    void onCrossPointClick(List<BaseEntity> entities, List<BaseChartData> chartsData);

}
