package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/21.
 */

public class CandleStickChartRender extends BaseRender<CandleStickChartData> {

    private static final float BAR_BLANK = 2;

    public CandleStickChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public void onMeasure() {
        super.onMeasure();
        measureCandleChartItemWidth();
    }

    private void measureCandleChartItemWidth() {
        if (mChart != null && mChart.getDataList() != null) {
            for (Object temp : mChart.getDataList()) {
                if (temp instanceof CandleStickChartData) {
                    CandleStickChartData data = (CandleStickChartData) temp;
                    CandleStickChartDataConfig config = data.getConfig();
                    float chartWidth = mChart.getxAxis().getEndPos().x -
                            mChart.getxAxis().getStartPos().x;
                    config.setBarWidth((int) (chartWidth / data.getData().size()));
                }
            }
        }
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        super.onDrawChart(canvas);
        drawCandleStickChart(canvas);
    }

    private void drawCandleStickChart(Canvas canvas) {
        if (mChart != null && mChart.getDataList() != null) {
            for (Object entity : mChart.getDataList()) {
                if (entity instanceof CandleStickChartData) {
                    drawSingleChart((CandleStickChartData) entity, canvas);
                }
            }
        }
    }

    private void drawSingleChart(CandleStickChartData data, Canvas canvas) {
        if (data.getData() != null) {
            CandleStickChartDataConfig config = data.getConfig();

            mGraphPaint.setStyle(Paint.Style.FILL);
            mGraphPaint.setStrokeWidth(config.getStrokeWidth());

            double range = data.getYMax() - data.getYMin();
            float chartHeight = 0;

            if (mChart != null && mChart.getyAxis().getScales() != null &&
                    mChart.getyAxis().getScales().size() >= 2) {
                List<Scale> scales = mChart.getyAxis().getScales();
                chartHeight = Math.abs(scales.get(0).getStartPos().y -
                        scales.get(scales.size() - 1).getEndPos().y);
                float startY = scales.get(scales.size() - 1).getEndPos().y;
                float endX = scales.get(scales.size() - 1).getEndPos().x;
                float startX = scales.get(0).getStartPos().x;

                float x1, y1, x2, y2;
                float highX, highY, lowX, lowY;
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    CandleStickEntity temp = data.getData().get(i);
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

                    double top, bottom;

                    if (temp.getOpen() > temp.getClose()) {
                        top = temp.getOpen();
                        bottom = temp.getClose();
                        mGraphPaint.setColor(config.getDecreasingColor());
                    } else {
                        top = temp.getClose();
                        bottom = temp.getOpen();
                        mGraphPaint.setColor(config.getIncreasingColor());
                    }
                    y1 = startY - (float) ((top - data.getYMin()) / range * chartHeight);
                    y2 = startY - (float) ((bottom - data.getYMin()) / range * chartHeight);

                    highX = lowX = x1 + config.getBarWidth() / 2 * mScale;
                    highY = startY - (float) ((temp.getHigh() - data.getYMin()) / range * chartHeight);
                    lowY = startY - (float) ((temp.getLow() - data.getYMin()) / range * chartHeight);

                    canvas.drawRect(x1 + BAR_BLANK, y1, x2, y2, mGraphPaint);
                    canvas.drawLine(highX + BAR_BLANK, highY, lowX + BAR_BLANK, lowY, mGraphPaint);
                }
            }
        }
    }

}
