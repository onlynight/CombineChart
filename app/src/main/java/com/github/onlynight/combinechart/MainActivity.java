package com.github.onlynight.combinechart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.github.onlynight.chartlibrary.chart.LineChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;
import com.github.onlynight.chartlibrary.view.CombineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CombineChartView mCombineChartView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCombineChartView = (CombineChartView) findViewById(R.id.chart);
        mCombineChartView.addChart(generateLineChart());
        mCombineChartView.addChart(generateLineChart1());
        mCombineChartView.addChart(generateLineChart2());

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = progress / 20f;
                if (scale > 7) {
                    scale = 7;
                }
                mCombineChartView.setScale(scale);
                mCombineChartView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCombineChartView.setxDelta(progress);
                mCombineChartView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private List<LineEntity> generateLineEntities() {
        List<LineEntity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            LineEntity entity = new LineEntity();
            entity.setX(i);
            entity.setY(random.nextFloat() * 1000);
            entities.add(entity);
        }
        return entities;
    }

    private LineChartData generateLineData() {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setColor(Color.RED);
        config.setStrokeWidth(1f);
        config.setXFormat("0");
        config.setYFormat("0.00");

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = generateLineEntities();
        data.setData(entities);

        return data;
    }

    private LineChart generateLineChart() {
        LineChart lineChart = new LineChart();
        lineChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_TOP);
        y.setTextColor(Color.BLACK);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

    private LineChart generateLineChart1() {
        LineChart lineChart = new LineChart();
        lineChart.setWeight(0.5f);
        lineChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setScaleLineType(Axis.LINE_TYPE_DASH);
        y.setHasScaleText(true);
        y.setHasLine(true);
        y.setTextColor(Color.BLACK);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_BOTTOM);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextColor(Color.BLACK);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

    private LineChart generateLineChart2() {
        LineChart lineChart = new LineChart();
        lineChart.setWeight(0.5f);
        lineChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        LineChartData data = generateLineData();
        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setGrid(4);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setTextColor(Color.BLACK);

        Axis x = lineChart.getxAxis();
        x.setPosition(Axis.POSITION_BOTTOM);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(true);
        x.setHasScaleText(true);
        x.setTextColor(Color.BLACK);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasRight(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

}
