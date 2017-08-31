package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/22.
 */

public class LinePartRender extends BasePartRender {

    public LinePartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (mChart.getDataList() != null) {
            for (int i = 0; i < mChart.getDataList().size(); i++) {
                Object data = mChart.getDataList().get(i);
                if (data instanceof LineChartData) {
                    drawLine(canvas, (LineChartData) data, i);
                }
            }
        }
    }

    private void drawLine(Canvas canvas, LineChartData data, int index) {
        if (data == null || data.getData() == null || data.getData().size() <= 0) {
            return;
        }

        mGraphPaint.setStyle(Paint.Style.STROKE);

        List<Scale> scales = mChart.getyAxis().getScales();
        float chartWidth = mChart.getxAxis().getEndPos().x -
                mChart.getxAxis().getStartPos().x;
        float chartHeight = scales.get(scales.size() - 1).getStartPos().y -
                scales.get(0).getStartPos().y;
        double maxValue = getYMaxValue();
        double minValue = getYMinValue();
        double valueRange = maxValue - minValue;
        List<LineEntity> entities = data.getData();
        LineChartDataConfig config = data.getConfig();
        int size = entities.size();

        float[] ptf = calculateChartPos(entities, data.getConfig(), 0,
                chartWidth, chartHeight, minValue, valueRange);
        float[] lastPt = new float[2];
        if (ptf != null) {
            lastPt[0] = ptf[0];
            lastPt[1] = ptf[1];
            if (config != null) {
                mGraphPaint.setStrokeWidth(config.getStrokeWidth());
                mGraphPaint.setColor(config.getColor());
            }
            for (int i = 0; i < size; i++) {
                float[] temp = calculateChartPos(entities, config, i,
                        chartWidth, chartHeight, minValue, valueRange);
                if (temp != null && temp[0] >= mChart.getxAxis().getStartPos().x &&
                        temp[0] <= mChart.getxAxis().getEndPos().x) {
                    canvas.drawLine(lastPt[0], lastPt[1], temp[0], temp[1], mGraphPaint);
                    lastPt[0] = temp[0];
                    lastPt[1] = temp[1];
                }
            }
        }
    }

    private float[] calculateChartPos(List<LineEntity> entities,
                                      LineChartDataConfig config, int index, float chartWidth,
                                      float chartHeight, double minValue, double valueRange) {
        List<Scale> scales = mChart.getyAxis().getScales();
        if (scales == null || scales.size() <= 0) {
            return null;
        }

        float blank;

        if (config.isAutoWidth()) {
            blank = chartWidth / (entities.size() - 1);
        } else {
            blank = config.getBarWidth();
        }

        LineEntity entity = entities.get(index);
        float y = scales.get(0).getStartPos().y +
                chartHeight - (float) ((entity.getY() - minValue) / valueRange * chartHeight);
        float x;
        switch (mChart.getyAxis().getPosition()) {
            case Axis.POSITION_RIGHT:
                x = mChart.getxAxis().getEndPos().x -
                        blank * (entities.size() - index - 1) * mScale;
                break;
            case Axis.POSITION_LEFT:
            default:
                x = blank * index * mScale + mChart.getxAxis().getStartPos().x;
                break;
        }
        x += mXDelta;
        entity.setX(x);
        float[] value = new float[2];
        value[0] = x;
        value[1] = y;

        return value;
    }

}
