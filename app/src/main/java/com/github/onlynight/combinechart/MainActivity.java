package com.github.onlynight.combinechart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.github.onlynight.chartlibrary.chart.BarChart;
import com.github.onlynight.chartlibrary.chart.CandleStickChart;
import com.github.onlynight.chartlibrary.chart.CombineChart;
import com.github.onlynight.chartlibrary.chart.LineChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.BarChartDataConfig;
import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;
import com.github.onlynight.chartlibrary.view.CombineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String MAX_Y_SCALE_TEXT = "0.0000000";

    private CombineChartView mCombineChartView;
    private SeekBar seekBar;

    private DataRepository mDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataRepository = new DataRepository();

        mCombineChartView = (CombineChartView) findViewById(R.id.chart);
        mCombineChartView.addChart(generateCombineChart1());
        mCombineChartView.addChart(generateCombineChart2());
        mCombineChartView.addChart(generateCombineChart3());
//        mCombineChartView.addChart(generateCandleChart());
//        mCombineChartView.addChart(generateLineChart());
//        mCombineChartView.addChart(generateBarChart());

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = progress / 25f;
                if (scale > 6) {
                    scale = 6;
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

    private LineChartData generateLineData(int lineColor) {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setColor(lineColor);
        config.setStrokeWidth(1f);
        config.setXFormat("0");
        config.setYFormat("0.00");

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = generateLineEntities();
        data.setData(entities);

        return data;
    }

    private LineChartData generateCombineLineData(int lineColor) {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setColor(lineColor);
        config.setStrokeWidth(2f);
        config.setXFormat("0");
        config.setYFormat("0.00");

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = mDataRepository.generateCombineLineEntities();
        data.setData(entities);

        return data;
    }

    private CombineChart generateCombineChart1() {
        CombineChart candleChart = new CombineChart();
        candleChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        candleChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        CandleStickChartData data = generateCandleChartData();
        candleChart.addData(data);
        LineChartData data1 = generateCombineLineData(Color.BLACK);
        candleChart.addData(data1);

        Axis y = candleChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = candleChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return candleChart;
    }

    private CombineChart generateCombineChart2() {
        CombineChart candleChart = new CombineChart();
        candleChart.setWeight(0.5f);
        candleChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        candleChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        BarChartData data = generateBarData();
        candleChart.addData(data);
        LineChartData data1 = generateCombineLineData(Color.BLACK);
        candleChart.addData(data1);

        Axis y = candleChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = candleChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return candleChart;
    }

    private CombineChart generateCombineChart3() {
        CombineChart candleChart = new CombineChart();
        candleChart.setWeight(0.5f);
        candleChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        candleChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        BarChartData data = generateBarData();
        candleChart.addData(data);
        LineChartData data1 = generateCombineLineData(Color.BLACK);
        candleChart.addData(data1);

        Axis y = candleChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getxAxis();
        x.setPosition(Axis.POSITION_BOTTOM);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(true);
        x.setHasScaleText(true);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = candleChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(true);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return candleChart;
    }

    private CandleStickChart generateCandleChart() {
        CandleStickChart candleChart = new CandleStickChart();
        candleChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        candleChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        CandleStickChartData data = generateCandleChartData();
        candleChart.addData(data);

        Axis y = candleChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = candleChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return candleChart;
    }

    private CandleStickChartData generateCandleChartData() {
        CandleStickChartDataConfig config = new CandleStickChartDataConfig();
        config.setBarWidth(10);
        config.setIncreasingColor(Color.RED);
        config.setDecreasingColor(Color.GREEN);
        config.setStrokeWidth(2f);
        config.setXFormat("0");
        config.setYFormat("0.00");

        CandleStickChartData data = new CandleStickChartData(config);
        List<CandleStickEntity> entities = mDataRepository.generateCandleStickEntities();
        data.setData(entities);

        return data;
    }

    private LineChart generateLineChart() {
        LineChart lineChart = new LineChart();
        lineChart.setWeight(0.5f);
        lineChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
        lineChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        LineChartData data = generateLineData(Color.RED);
        lineChart.addData(data);
//        data = generateLineData(Color.GREEN);
//        lineChart.addData(data);
//        data = generateLineData(Color.BLUE);
//        lineChart.addData(data);
//        data = generateLineData(Color.BLACK);
//        lineChart.addData(data);

        Axis y = lineChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(3);
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
        x.setHasScaleLine(false);
        x.setTextColor(Color.BLACK);

        Border border = lineChart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        border.setColor(Color.GRAY);

        return lineChart;
    }

    private BarChart generateBarChart() {
        BarChart barChart = new BarChart();
        barChart.setWeight(0.5f);
        barChart.setMarginTextSize(50);
        barChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        BarChartData data = generateBarData();
        barChart.addData(data);

        Axis y = barChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setGrid(2);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setTextColor(Color.BLACK);

        Axis x = barChart.getxAxis();
        x.setPosition(Axis.POSITION_BOTTOM);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(true);
        x.setHasScaleText(true);
        x.setHasScaleLine(false);
        x.setTextColor(Color.BLACK);

        Border border = barChart.getBorder();
        border.setWidth(1);
        border.setHasRight(false);
        border.setColor(Color.GRAY);

        return barChart;
    }

    private BarChartData generateBarData() {
        BarChartDataConfig config = new BarChartDataConfig();
        config.setStrokeWidth(1);
        config.setXFormat("0");
        config.setYFormat("0.00");
        BarChartData data = new BarChartData(config);

        List<BarEntity> entities = mDataRepository.generateBarEntities();
//        Random random = new Random();
//        List<BarEntity> entities = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            BarEntity entity = new BarEntity();
//            entity.setX(i);
//            entity.setY(random.nextFloat() * 1000);
//            entity.setColor(i % 2 == 0 ? Color.RED : Color.GREEN);
//            entities.add(entity);
//        }
        data.setData(entities);

        return data;
    }

}
