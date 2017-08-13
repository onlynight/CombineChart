package com.github.onlynight.chartlibrary.data.config;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseChartDataConfig {

    private int mColor;
    private float mStrokeWidth;

    private String mXFormat = "0";
    private String mYFormat = "0";

    public String getXFormat() {
        return mXFormat;
    }

    public void setXFormat(String xFormat) {
        this.mXFormat = xFormat;
    }

    public String getYFormat() {
        return mYFormat;
    }

    public void setYFormat(String yFormat) {
        this.mYFormat = yFormat;
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

}
