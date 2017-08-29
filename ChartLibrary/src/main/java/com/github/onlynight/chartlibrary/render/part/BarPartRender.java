package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.data.config.BarChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/22.
 */

public class BarPartRender extends BasePartRender {

    public BarPartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public void onMeasure() {
        measureBarWidth();
    }

    private void measureBarWidth() {
        if (mChart != null && mChart.getDataList() != null) {
            float chartWidth = 0;
            for (Object temp : mChart.getDataList()) {
                if (temp instanceof BarChartData) {
                    BarChartData data = (BarChartData) temp;
                    BarChartDataConfig config = data.getConfig();
                    if (config.isAutoWidth()) {
                        Axis xAxis = mChart.getxAxis();
                        chartWidth = xAxis.getEndPos().x - xAxis.getStartPos().x;
                        if (data.getData() != null &&
                                data.getData().size() > 0) {
                            config.setBarWidth(chartWidth / (float) data.getData().size());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        drawBar(canvas);
    }

    private void drawBar(Canvas canvas) {
        if (mChart != null && mChart.getDataList() != null) {
            for (Object data : mChart.getDataList()) {
                if (data instanceof BarChartData) {
                    drawSingleChart((BarChartData) data, canvas);
                }
            }
        }
    }

    private void drawSingleChart(BarChartData data, Canvas canvas) {
        if (data.getData() != null) {
            mGraphPaint.setStyle(Paint.Style.FILL);

            BarChartDataConfig config = data.getConfig();
            double range = data.getYMax() - data.getYMin();
            float chartHeight = 0;

            if (mChart != null && mChart.getyAxis().getScales() != null &&
                    mChart.getyAxis().getScales().size() >= 2) {
                List<Scale> scales = mChart.getyAxis().getScales();
                chartHeight = Math.abs(scales.get(0).getStartPos().y -
                        scales.get(scales.size() - 1).getEndPos().y);
                float startY = scales.get(scales.size() - 1).getEndPos().y;
                float endX = mChart.getxAxis().getEndPos().x;
                float startX = mChart.getxAxis().getStartPos().x;

                float x1, y1, x2, y2 = startY;
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    BarEntity temp = data.getData().get(i);
                    switch (mChart.getyAxis().getPosition()) {
                        case Axis.POSITION_RIGHT:
                            x1 = endX - ((size - i) * config.getBarWidth()) * mScale
                                    + mXDelta;
                            x2 = endX - ((size - i - 1) * config.getBarWidth()) * mScale
                                    + mXDelta;
                            break;
                        case Axis.POSITION_LEFT:
                        default:
                            x1 = startX + (i * config.getBarWidth()) * mScale
                                    + mXDelta;
                            x2 = startX + ((i + 1) * config.getBarWidth()) * mScale
                                    + mXDelta;
                            break;
                    }
                    y1 = startY - (float) ((temp.getY() - data.getYMin()) / range * chartHeight);
                    mGraphPaint.setColor(temp.getColor());
                    temp.setX(x1);

                    if (x1 >= mChart.getxAxis().getStartPos().x &&
                            x1 <= mChart.getxAxis().getEndPos().x) {
                        canvas.drawRect(x1 + DEFAULT_BAR_WIDTH, y1, x2, y2, mGraphPaint);
                    }
                }
            }
        }
    }

}
