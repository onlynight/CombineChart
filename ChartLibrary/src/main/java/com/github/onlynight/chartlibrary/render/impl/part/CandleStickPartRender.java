package com.github.onlynight.chartlibrary.render.impl.part;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.onlynight.chartlibrary.chart.impl.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/22.
 */

public class CandleStickPartRender extends BasePartRender {

    private static final float BAR_BLANK = 2;

    public CandleStickPartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public void onDrawChart(Canvas canvas) {
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
        if (data.getShowData() != null) {
            CandleStickChartDataConfig config = data.getConfig();

            mGraphPaint.setStyle(Paint.Style.FILL);
            mGraphPaint.setStrokeWidth(config.getStrokeWidth());

            double range = data.getYMax() - data.getYMin();
            float chartHeight = 0;

            if (mChart != null && mChart.getYAxis().getScales() != null &&
                    mChart.getYAxis().getScales().size() >= 2) {
                List<Scale> scales = mChart.getYAxis().getScales();
                chartHeight = Math.abs(scales.get(0).getStartPos().y -
                        scales.get(scales.size() - 1).getEndPos().y);
                float startY = scales.get(scales.size() - 1).getEndPos().y;
                float endX = mChart.getXAxis().getEndPos().x;
                float startX = mChart.getXAxis().getStartPos().x;

                float x1, y1, x2, y2;
                float highX, highY, lowX, lowY;
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    CandleStickEntity entity = data.getData().get(i);
                    switch (mChart.getYAxis().getPosition()) {
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

                    if (entity.getOpen() > entity.getClose()) {
                        top = entity.getOpen();
                        bottom = entity.getClose();
                        mGraphPaint.setColor(config.getDecreasingColor());
                    } else {
                        top = entity.getClose();
                        bottom = entity.getOpen();
                        mGraphPaint.setColor(config.getIncreasingColor());
                    }
                    y1 = startY - (float) ((top - data.getYMin()) / range * chartHeight);
                    y2 = startY - (float) ((bottom - data.getYMin()) / range * chartHeight);

                    highX = lowX = x1 + config.getBarWidth() / 2 * mScale;
                    highY = startY - (float) ((entity.getHigh() - data.getYMin()) / range * chartHeight);
                    lowY = startY - (float) ((entity.getLow() - data.getYMin()) / range * chartHeight);

                    int tempX = (int) (highX + BAR_BLANK - config.getStrokeWidth() / 2);

                    // if open equals close
                    if (y1 == y2) {
                        y2 += 2;
                    }

                    if (x1 >= mChart.getXAxis().getStartPos().x &&
                            x1 <= mChart.getXAxis().getEndPos().x) {
                        canvas.drawRect(x1 + BAR_BLANK, y1, x2, y2, mGraphPaint);
                        canvas.drawLine(tempX, highY, tempX, lowY, mGraphPaint);
                    }

                    entity.setX(highX);
                }
            }
        }

        drawMinAndMax(data, canvas);
    }

    private void drawMinAndMax(CandleStickChartData data, Canvas canvas) {
        if (data.getShowData() != null &&
                data.getShowData().size() > data.getMaxIndex() &&
                mChart.getYAxis().getScales() != null &&
                mChart.getYAxis().getScales().size() > 0) {

            mTextPaint.setTextSize(mChart.getMarginTextSize());
            mTextPaint.setColor(mChart.getMarginTextColor());

            CandleStickEntity minEntity =
                    data.getShowData().get(data.getMinIndex());
            CandleStickEntity maxEntity =
                    data.getShowData().get(data.getMaxIndex());

            String low;
            if (data.getConfig().getYValueFormatter() != null) {
                low = data.getConfig().getYValueFormatter().format(minEntity.getLow());
            } else {
                low = String.valueOf(minEntity.getLow());
            }

            float textWidth = mTextPaint.measureText(low);
            double tempX = minEntity.getX() + textWidth;
            if (tempX > mChart.getXAxis().getEndPos().x) {
                tempX = minEntity.getX() - textWidth;
            } else {
                tempX = minEntity.getX();
            }

            int size = mChart.getYAxis().getScales().size() - 1;

            if (size < 0) {
                size = 0;
            }

            canvas.drawText(low, (float) tempX,
                    mChart.getYAxis().getScales().
                            get(size).getStartPos().y +
                            BaseChart.BLANK + getFontHeight(mTextPaint), mTextPaint);

            String high;
            if (data.getConfig().getYValueFormatter() != null) {
                high = data.getConfig().getYValueFormatter().format(maxEntity.getHigh());
            } else {
                high = String.valueOf(maxEntity.getHigh());
            }
            textWidth = mTextPaint.measureText(high);
            tempX = maxEntity.getX() + textWidth;
            if (tempX > mChart.getXAxis().getEndPos().x) {
                tempX = maxEntity.getX() - textWidth;
            } else {
                tempX = maxEntity.getX();
            }

            canvas.drawText(high, (float) tempX,
                    mChart.getTop() + BaseChart.BLANK + getFontHeight(mTextPaint), mTextPaint);
        }
    }

}
