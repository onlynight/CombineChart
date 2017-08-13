package com.github.onlynight.combinechart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.chart.LineChart;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;
import com.github.onlynight.chartlibrary.view.CombineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CombineChartView mCombineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCombineChartView = (CombineChartView) findViewById(R.id.chart);
        mCombineChartView.addChart(generateLineChart());
        mCombineChartView.addChart(generateLineChart1());
        mCombineChartView.addChart(generateLineChart2());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateLineChart());
    }

    private List<LineEntity> generateLineEntities() {
        List<LineEntity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            LineEntity entity = new LineEntity();
            entity.setX(i);
            entity.setY(random.nextFloat() * 1000);
            entities.add(entity);
        }
        return entities;
    }

    private LineChartData generateLineData() {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setColor(Color.GRAY);
        config.setStrokeWidth(2f);
        config.setXFormat("0");
        config.setYFormat("0.00");

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = generateLineEntities();
        data.setData(entities);

        return data;
    }

    private LineChart generateLineChart() {
        LineChart lineChart = new LineChart();
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_LEFT);
        y.setColor(Color.GRAY);
        y.setWidth(1);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setTextColor(Color.GRAY);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.GRAY);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setTextColor(Color.GRAY);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

    private LineChart generateLineChart1() {
        LineChart lineChart = new LineChart();
        lineChart.setWeight(0.5f);
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_LEFT);
        y.setColor(Color.GRAY);
        y.setWidth(1);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setTextColor(Color.GRAY);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.GRAY);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setTextColor(Color.GRAY);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

    private LineChart generateLineChart2() {
        LineChart lineChart = new LineChart();
        lineChart.setWeight(0.5f);
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_LEFT);
        y.setColor(Color.GRAY);
        y.setWidth(1);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setTextColor(Color.GRAY);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.GRAY);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setTextColor(Color.GRAY);

        Border border = lineChart.getBorder();
        border.setWidth(1);
//        border.setHasBottom(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

}
