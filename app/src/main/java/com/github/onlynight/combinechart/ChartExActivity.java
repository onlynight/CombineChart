package com.github.onlynight.combinechart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.onlynight.chartlibrary.view.CombineChartView;

public class ChartExActivity extends AppCompatActivity {

    private CombineChartView mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_ex);

        mChart = (CombineChartView) findViewById(R.id.chart);
    }

}
