package com.github.onlynight.chartlibrary.data.config;

/**
 * Created by lion on 2017/8/10.
 */

public class CandleStickChartDataConfig extends BaseChartDataConfig {

    private int decreasingColor;
    private int increasingColor;

    private int mBarWidth;

    public int getBarWidth() {
        return mBarWidth;
    }

    public void setBarWidth(int barWidth) {
        this.mBarWidth = barWidth;
    }

    public int getDecreasingColor() {
        return decreasingColor;
    }

    public void setDecreasingColor(int decreasingColor) {
        this.decreasingColor = decreasingColor;
    }

    public int getIncreasingColor() {
        return increasingColor;
    }

    public void setIncreasingColor(int increasingColor) {
        this.increasingColor = increasingColor;
    }

}
