package com.github.onlynight.chartlibrary.chart;

import android.graphics.PointF;

/**
 * Created by lion on 2017/8/21.
 */

public interface IChart {

    float getScale();

    void setScale(float scale);

    float getXDelta();

    void setXDelta(float xDelta);

    boolean isCanZoomLessThanNormal();

    void setCanZoomLessThanNormal(boolean canZoomLessThanNormal);

    void setCrossPoint(PointF crossPoint);

    PointF getCrossPoint();

    void setCrossColor(int crossColor);

    int getCrossBorderColor();

    void setCrossLineWidth(float crossLineWidth);

    void setCrossBorderColor(int color);

}
