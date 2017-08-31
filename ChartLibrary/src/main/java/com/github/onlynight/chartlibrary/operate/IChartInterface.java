package com.github.onlynight.chartlibrary.operate;

import android.graphics.PointF;

/**
 * Created by lion on 2017/8/21.
 */

public interface IChartInterface {

    float getScale();

    void setScale(float scale);

    float getxDelta();

    void setxDelta(float xDelta);

    boolean isCanZoomLessThanNormal();

    void setCanZoomLessThanNormal(boolean canZoomLessThanNormal);

    void setCrossPoint(PointF crossPoint);

    void setCrossColor(int crossColor);

    void setCrossLineWidth(float crossLineWidth);

    void setCrossBorderColor(int color);

}
