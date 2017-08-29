package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/22.
 */

public class LinePartRender extends BasePartRender {

    private List<Path> paths;

    public LinePartRender(BaseChart chart) {
        super(chart);
        this.paths = new ArrayList<>();
    }

    @Override
    public void onMeasure() {
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

    private Path getPath(int index) {
        if (index < paths.size()) {
            Path path = paths.get(index);
            path.reset();
            return path;
        } else {
            Path path = new Path();
            paths.add(path);
            return path;
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

//        Path path = getPath(index);
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

//    private void drawLine(Canvas canvas, LineChartData data, int index) {
//        if (data == null || data.getData() == null || data.getData().size() <= 0) {
//            return;
//        }
//
//        mGraphPaint.setStyle(Paint.Style.STROKE);
//
//        List<LineEntity> entities = data.getData();
//        Path path = getPath(index);
//        float[] ptf = calculateChartPos(entities, data.getConfig(), 0);
//        if (ptf != null) {
//            LineChartDataConfig config = data.getConfig();
//            path.moveTo(ptf[0], ptf[1]);
//            for (int i = 0; i < entities.size(); i++) {
//                float[] temp = calculateChartPos(entities, config, i);
//                if (temp != null && temp[0] >= mChart.getxAxis().getStartPos().x &&
//                        temp[0] <= mChart.getxAxis().getEndPos().x) {
//                    path.lineTo(temp[0], temp[1]);
//                }
//            }
//            if (config != null) {
//                mGraphPaint.setStrokeWidth(config.getStrokeWidth());
//                mGraphPaint.setColor(config.getColor());
//                mGraphPaint.setPathEffect(null);
//            }
//
//            canvas.drawPath(path, mGraphPaint);
//        }
//    }
//
//    private float[] calculateChartPos(List<LineEntity> entities,
//                                      LineChartDataConfig config, int index) {
//        List<Scale> scales = mChart.getyAxis().getScales();
//        if (scales == null || scales.size() <= 0) {
//            return null;
//        }
//
//        float chartWidth = mChart.getxAxis().getEndPos().x -
//                mChart.getxAxis().getStartPos().x;
//        float chartHeight = scales.get(scales.size() - 1).getStartPos().y -
//                scales.get(0).getStartPos().y;
//        double maxValue = getYMaxValue();
//        double minValue = getYMinValue();
//        double valueRange = maxValue - minValue;
//
//        float blank;
//
//        if (config.isAutoWidth()) {
//            blank = chartWidth / (entities.size() - 1);
//        } else {
//            blank = config.getBarWidth();
//        }
//
//        LineEntity entity = entities.get(index);
//        float y = scales.get(0).getStartPos().y +
//                chartHeight - (float) ((entity.getY() - minValue) / valueRange * chartHeight);
//        float x;
//        switch (mChart.getyAxis().getPosition()) {
//            case Axis.POSITION_RIGHT:
//                x = mChart.getxAxis().getEndPos().x -
//                        blank * (entities.size() - index - 1) * mScale;
//                break;
//            case Axis.POSITION_LEFT:
//            default:
//                x = blank * index * mScale + mChart.getxAxis().getStartPos().x;
//                break;
//        }
//        x += mXDelta;
//        entity.setX(x);
//        float[] value = new float[2];
//        value[0] = x;
//        value[1] = y;
//
//        return value;
//    }

}
