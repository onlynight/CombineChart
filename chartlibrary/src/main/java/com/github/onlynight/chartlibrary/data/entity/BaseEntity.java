package com.github.onlynight.chartlibrary.data.entity;

/**
 * Created by lion on 2017/8/10.
 */

public class BaseEntity {

    private double mX;
    private double mY;

    private double mYMin;
    private double mYMax;

    private String mXValue;
    private String mYValue;

    public double getX() {
        return mX;
    }

    public void setX(double x) {
        this.mX = x;
    }

    public double getY() {
        return mY;
    }

    public void setY(double y) {
        this.mY = y;
    }

    public String getxValue() {
        return mXValue;
    }

    public void setxValue(String xValue) {
        this.mXValue = xValue;
    }

    public String getyValue() {
        return mYValue;
    }

    public void setyValue(String yValue) {
        this.mYValue = yValue;
    }

    public double getyMin() {
        return mYMin;
    }

    public void setyMin(double min) {
        this.mYMin = min;
    }

    public double getyMax() {
        return mYMax;
    }

    public void setyMax(double max) {
        this.mYMax = max;
    }
}
