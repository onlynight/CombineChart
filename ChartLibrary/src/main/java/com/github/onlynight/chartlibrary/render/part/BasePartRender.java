package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.operate.IChartInterface;

/**
 * Created by lion on 2017/8/22.
 */

public abstract class BasePartRender implements IChartInterface {

    public static final int DEFAULT_BAR_WIDTH = 2;

    protected BaseChart mChart;

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected float mScale = 1f;
    protected float mXDelta = 0;

    public BasePartRender(BaseChart chart) {
        this.mChart = chart;
    }

    public abstract void onMeasure();

    public abstract void onDrawChart(Canvas canvas);

    @Override
    public void setxDelta(float xDelta) {
        this.mXDelta = xDelta;
    }

    @Override
    public float getxDelta() {
        return mXDelta;
    }

    @Override
    public void setScale(float scale) {
        this.mScale = scale;
    }

    @Override
    public float getScale() {
        return mScale;
    }

    protected double getYMaxValue() {
        double max = Double.MIN_VALUE;
        for (Object obj : mChart.getDataList()) {
            if (obj instanceof BaseChartData) {
                double temp = ((BaseChartData) obj).getYMax();
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max;
    }

    protected double getYMinValue() {
        double min = Double.MAX_VALUE;
        for (Object obj : mChart.getDataList()) {
            if (obj instanceof BaseChartData) {
                double temp = ((BaseChartData) obj).getYMin();
                if (temp < min) {
                    min = temp;
                }
            }
        }

        return min;
    }

    /**
     * @return get font height
     */
    protected float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }

}
