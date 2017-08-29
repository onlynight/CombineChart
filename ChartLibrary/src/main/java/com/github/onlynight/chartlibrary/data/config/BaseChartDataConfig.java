package com.github.onlynight.chartlibrary.data.config;

import com.github.onlynight.chartlibrary.data.formatter.ValueFormatter;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseChartDataConfig {

    private int mColor;
    private float mStrokeWidth;
    private float mBarWidth = 15f;
    private boolean mIsAutoWidth = false;
    private ValueFormatter mXValueFormatter;
    private ValueFormatter mYValueFormatter;

    public boolean isAutoWidth() {
        return mIsAutoWidth;
    }

    public void setAutoWidth(boolean autoWidth) {
        mIsAutoWidth = autoWidth;
    }

    public float getBarWidth() {
        return mBarWidth;
    }

    public void setBarWidth(float barWidth) {
        this.mBarWidth = barWidth;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    public ValueFormatter getXValueFormatter() {
        return mXValueFormatter;
    }

    public void setXValueFormatter(ValueFormatter mXValueFormatter) {
        this.mXValueFormatter = mXValueFormatter;
    }

    public ValueFormatter getYValueFormatter() {
        return mYValueFormatter;
    }

    public void setYValueFormatter(ValueFormatter mYValueFormatter) {
        this.mYValueFormatter = mYValueFormatter;
    }
}
