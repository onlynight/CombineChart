package com.github.onlynight.combinechart.chart;

import android.content.Context;
import android.graphics.Color;

import com.github.onlynight.chartlibrary.chart.CombineChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.combinechart.R;
import com.github.onlynight.combinechart.repository.DataRepository;
import com.github.onlynight.combinechart.repository.KLineRepository;

/**
 * Created by lion on 2017/8/29.
 */

public class KLineChartFactory {

    private static final String MAX_Y_SCALE_TEXT = "0.00000000";

    private Context mContext;

    private DataRepository mDataRepository;
    private KLineRepository mKLineRepository;

    public KLineChartFactory(Context mContext) {
        this.mContext = mContext;
        this.mDataRepository = new DataRepository();
        this.mKLineRepository = new KLineRepository(mDataRepository.createLocalCandleStickData(0));
    }

    public CombineChart generateKLineChart() {
        CombineChart candleChart = new CombineChart();
        candleChart.setMarginTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        candleChart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        CandleStickChartData data = mKLineRepository.getCandleStickChartData();
        data.getConfig().setAutoWidth(false);
        candleChart.addData(data);
        candleChart.addData(mKLineRepository.getMa5LineChartData());
        candleChart.addData(mKLineRepository.getMa10LineChartData());
        candleChart.addData(mKLineRepository.getMa20LineChartData());

        Axis y = candleChart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
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

    public CombineChart generateVolChart() {
        CombineChart chart = new CombineChart();
        chart.setWeight(0.5f);
        chart.setMarginTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        chart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        chart.addData(mKLineRepository.getVolBarChartData());
        chart.addData(mKLineRepository.getVolMA5LineChartData());
        chart.addData(mKLineRepository.getVolMA10LineChartData());
        chart.addData(mKLineRepository.getVolMA20LineChartData());

        Axis y = chart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        y.setGrid(3);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = chart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = chart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return chart;
    }

    public CombineChart generateTechChart() {
        CombineChart chart = new CombineChart();
        chart.setWeight(0.5f);
        chart.setMarginTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        chart.setMaxYAxisScaleText(MAX_Y_SCALE_TEXT);
        Axis y = chart.getyAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        y.setGrid(3);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = chart.getxAxis();
        x.setPosition(Axis.POSITION_TOP);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(mContext.getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(false);
        x.setHasScaleText(false);
        x.setTextGravity(Axis.TEXT_GRAVITY_RIGHT);
        x.setTextColor(Color.BLACK);

        Border border = chart.getBorder();
        border.setWidth(1);
        border.setHasBottom(false);
        border.setHasRight(false);
        x.setHasScaleLine(false);
        border.setColor(Color.GRAY);

        return chart;
    }

}
