package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseChartData<Entity extends BaseEntity, Config extends BaseChartDataConfig> {

    protected List<Entity> mData;
    protected List<Entity> mShowData;
    protected Config mConfig;

    protected double mYMin;
    protected double mYMax;

    protected int mMaxIndex;
    protected int mMinIndex;

    public BaseChartData() {
    }

    public BaseChartData(List<Entity> data) {
        this.mData = data;
    }

    public BaseChartData(Config config) {
        this.mConfig = config;
    }

    public int getMaxIndex() {
        return mMaxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.mMaxIndex = maxIndex;
    }

    public int getMinIndex() {
        return mMinIndex;
    }

    public void setMinIndex(int minIndex) {
        this.mMinIndex = minIndex;
    }

    public List<Entity> getShowData() {
        return mShowData;
    }

    public void setShowData(List<Entity> showData) {
        this.mShowData = showData;
        DecimalFormat xdf = new DecimalFormat(mConfig.getXFormat());
        DecimalFormat ydf = new DecimalFormat(mConfig.getYFormat());
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for (int i = 0; i < showData.size(); i++) {
            Entity entity = mShowData.get(i);
            if (entity.getY() < min) {
                min = entity.getY();
                mMinIndex = i;
            }

            if (entity.getY() > max) {
                max = entity.getY();
                mMaxIndex = i;
            }

            entity.setxValue(xdf.format(entity.getX()));
            entity.setyValue(ydf.format(entity.getY()));
        }

        mYMax = max;
        mYMin = min;
    }

    public double getYMin() {
        return mYMin;
    }

    public void setYMin(double YMin) {
        this.mYMin = YMin;
    }

    public double getYMax() {
        return mYMax;
    }

    public void setYMax(double YMax) {
        this.mYMax = YMax;
    }

    public List<Entity> getData() {
        return mData;
    }

    public void setData(List<Entity> data) {
        this.mData = data;
        setShowData(data);
    }

    public Config getConfig() {
        return mConfig;
    }

}
