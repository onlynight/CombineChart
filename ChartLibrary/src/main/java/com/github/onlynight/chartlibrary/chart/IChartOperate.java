package com.github.onlynight.chartlibrary.chart;

import android.graphics.PointF;

/**
 * Created by lion on 2017/9/11.
 */

public interface IChartOperate {

    /**
     * get chart scale
     *
     * @return scale size
     */
    float getScale();

    /**
     * set chart scale
     *
     * @param scale scale size
     */
    void setScale(float scale);

    /**
     * get x delta
     *
     * @return x delta
     */
    float getXDelta();

    /**
     * set x delta
     *
     * @param xDelta x delta
     */
    void setXDelta(float xDelta);

    /**
     * if the candle stick bar num is less than the normal,
     * then it will can't zoom less than normal
     *
     * @return can zoom
     */
    boolean isCanZoomLessThanNormal();

    /**
     * set can zoom less than normal
     *
     * @param canZoomLessThanNormal can zoom
     */
    void setCanZoomLessThanNormal(boolean canZoomLessThanNormal);

    /**
     * set the cross poinht
     *
     * @param crossPoint cross point
     */
    void setCrossPoint(PointF crossPoint);

    /**
     * get cross point
     *
     * @return cross point
     */
    PointF getCrossPoint();

    /**
     * set cross line color
     *
     * @param crossColor cross line color
     */
    void setCrossColor(int crossColor);

    /**
     * get the cross line color
     *
     * @return cross line color
     */
    int getCrossBorderColor();

    /**
     * get cross line width
     *
     * @param crossLineWidth cross line width
     */
    void setCrossLineWidth(float crossLineWidth);

    /**
     * set cross end bar color
     *
     * @param color cross end bar color
     */
    void setCrossBorderColor(int color);

}
