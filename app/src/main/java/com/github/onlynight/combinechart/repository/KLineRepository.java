package com.github.onlynight.combinechart.repository;

import android.graphics.Color;

import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.BarChartDataConfig;
import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;
import com.github.onlynight.combinechart.formatter.TimeValueFormatter;
import com.github.onlynight.combinechart.formatter.YValueFormatter;

import java.util.ArrayList;
import java.util.List;

import static com.github.onlynight.chartlibrary.util.Utils.initMA;

/**
 * Created by lion on 2017/8/29.
 */

public class KLineRepository {

    private CandleStickChartData candleStickChartData;
    private LineChartData ma5LineChartData;
    private LineChartData ma10LineChartData;
    private LineChartData ma20LineChartData;

    private BarChartData volBarChartData;
    private LineChartData volMA5LineChartData;
    private LineChartData volMA10LineChartData;
    private LineChartData volMA20LineChartData;

    public KLineRepository(List<CandleStickEntity> candleStickEntities) {
        candleStickChartData = generateCandleStickData(candleStickEntities);
        generateMALineChartData();

        volBarChartData = generateVolBarChartData(candleStickEntities);
        generateVolMALineChartData(volBarChartData);
    }

    private CandleStickChartData generateCandleStickData(List<CandleStickEntity> candleStickEntities) {
        CandleStickChartDataConfig config = new CandleStickChartDataConfig();
        config.setAutoWidth(true);
        config.setIncreasingColor(Color.RED);
        config.setDecreasingColor(Color.GREEN);
        config.setStrokeWidth(2f);
        YValueFormatter formatter = new YValueFormatter();
        formatter.setFormat("0.00000000");
        config.setYValueFormatter(formatter);
        config.setXValueFormatter(new TimeValueFormatter());

        CandleStickChartData data = new CandleStickChartData(config);
        data.setData(candleStickEntities);

        return data;
    }

    private void generateMALineChartData() {
        ma5LineChartData = generateMALineChartData(5, Color.BLACK);
        ma10LineChartData = generateMALineChartData(10, Color.YELLOW);
        ma20LineChartData = generateMALineChartData(20, Color.MAGENTA);
    }


    private LineChartData generateMALineChartData(int days, int lineColor) {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setYValueFormatter(new YValueFormatter());
        config.setXValueFormatter(new TimeValueFormatter());
        config.setColor(lineColor);
        config.setStrokeWidth(2f);
        config.setAutoWidth(false);

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = initMA(days, candleStickChartData);
        data.setData(entities);

        return data;
    }

    private BarChartData generateVolBarChartData(List<CandleStickEntity> candleStickEntities) {
        if (candleStickEntities == null) {
            return null;
        }

        BarChartDataConfig config = new BarChartDataConfig();
        config.setStrokeWidth(1);
        config.setAutoWidth(false);
        YValueFormatter formatter = new YValueFormatter();
        formatter.setFormat("0.000");
        config.setYValueFormatter(formatter);
        config.setXValueFormatter(new TimeValueFormatter());
        BarChartData data = new BarChartData(config);

        List<BarEntity> entities = new ArrayList<>();
        int i = 0;
        for (CandleStickEntity temp : candleStickEntities) {
            BarEntity entity = new BarEntity();
            entity.setX(i);
            entity.setY(temp.getVol());
            entity.setTime(temp.getTime());
            if (temp.getOpen() > temp.getClose()) {
                entity.setColor(Color.GREEN);
            } else {
                entity.setColor(Color.RED);
            }
            entities.add(entity);
            i++;
        }
        data.setData(entities);

        return data;
    }

    private void generateVolMALineChartData(BarChartData volBarChartData) {
        volMA5LineChartData = generateVolMALineChartData(volBarChartData, 5, Color.BLACK);
        volMA10LineChartData = generateVolMALineChartData(volBarChartData, 10, Color.YELLOW);
        volMA20LineChartData = generateVolMALineChartData(volBarChartData, 20, Color.MAGENTA);
    }

    private LineChartData generateVolMALineChartData(BarChartData volBarChartData,
                                                     int days, int lineColor) {
        if (volBarChartData == null) {
            return null;
        }

        LineChartDataConfig config = new LineChartDataConfig();
        config.setYValueFormatter(new YValueFormatter());
        config.setXValueFormatter(new TimeValueFormatter());
        config.setColor(lineColor);
        config.setStrokeWidth(2f);
        config.setAutoWidth(false);

        LineChartData data = new LineChartData(config);
        List<LineEntity> entities = initMA(days, volBarChartData);
        data.setData(entities);

        return data;
    }

    public CandleStickChartData getCandleStickChartData() {
        return candleStickChartData;
    }

    public LineChartData getMa5LineChartData() {
        return ma5LineChartData;
    }

    public LineChartData getMa10LineChartData() {
        return ma10LineChartData;
    }

    public LineChartData getMa20LineChartData() {
        return ma20LineChartData;
    }

    public BarChartData getVolBarChartData() {
        return volBarChartData;
    }

    public LineChartData getVolMA5LineChartData() {
        return volMA5LineChartData;
    }

    public LineChartData getVolMA10LineChartData() {
        return volMA10LineChartData;
    }

    public LineChartData getVolMA20LineChartData() {
        return volMA20LineChartData;
    }
}
