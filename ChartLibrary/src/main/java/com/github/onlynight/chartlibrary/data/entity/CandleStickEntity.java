package com.github.onlynight.chartlibrary.data.entity;

/**
 * Created by lion on 2017/8/10.
 */

public class CandleStickEntity extends BaseEntity {

    private double mHigh;
    private double mLow;
    private double mOpen;
    private double mClose;
    private long mTime;

    private int mColor;

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public double getHigh() {
        return mHigh;
    }

    public void setHigh(double high) {
        this.mHigh = high;
        setY(high);
    }

    public double getLow() {
        return mLow;
    }

    public void setLow(double low) {
        this.mLow = low;
    }

    public double getOpen() {
        return mOpen;
    }

    public void setOpen(double open) {
        this.mOpen = open;
    }

    public double getClose() {
        return mClose;
    }

    public void setClose(double close) {
        this.mClose = close;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

}
