package com.github.onlynight.chartlibrary.data;

import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

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

    /**
     * chart name
     */
    protected String mChartName = "Chart";

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
        reformatData();
    }

    public String getChartName() {
        return mChartName;
    }

    public void setChartName(String chartName) {
        this.mChartName = chartName;
    }

    public void reformatData() {
        if (mShowData != null) {
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            for (int i = 0; i < mShowData.size(); i++) {
                Entity entity = mShowData.get(i);
                if (entity.getY() < min) {
                    min = entity.getY();
                    mMinIndex = i;
                }

                if (entity.getY() > max) {
                    max = entity.getY();
                    mMaxIndex = i;
                }

                if (mConfig.getXValueFormatter() != null) {
                    entity.setxValue(mConfig.getXValueFormatter().format(entity));
                } else {
                    entity.setxValue(String.valueOf(entity.getX()));
                }

                if (mConfig.getYValueFormatter() != null) {
                    entity.setyValue(mConfig.getYValueFormatter().format(entity));
                } else {
                    entity.setyValue(String.valueOf(entity.getY()));
                }
            }

            if (min == max) {
                max += getMaxDelta(max);
            }

            mYMax = max;
            mYMin = min;
        }
    }

    private double getMaxDelta(double max) {
        if (max > 0.1) {
            return 0.1;
        } else if (max > 0.01) {
            return 0.01;
        } else if (max > 0.001) {
            return 0.001;
        } else if (max > 0.0001) {
            return 0.0001;
        } else if (max > 0.00001) {
            return 0.00001;
        } else if (max > 0.000001) {
            return 0.000001;
        } else if (max > 0.0000001) {
            return 0.0000001;
        } else if (max > 0.00000001) {
            return 0.00000001;
        }

        return 0;
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

    public void setConfig(Config mConfig) {
        this.mConfig = mConfig;
    }
}
