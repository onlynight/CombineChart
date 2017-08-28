package com.github.onlynight.chartlibrary.chart.part;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * Created by lion on 2017/8/11.
 * the chart's border part
 */

public class Border {

    /**
     * border line width
     */
    private float mWidth = 1f;

    /**
     * border line color
     */
    private int mColor = Color.BLACK;

    /**
     * border's position
     */
    private PointF topLeft = new PointF();
    private PointF bottomRight = new PointF();

    /**
     * has the left border
     */
    private boolean hasLeft = true;

    /**
     * has the top border
     */
    private boolean hasTop = true;

    /**
     * has the right border
     */
    private boolean hasRight = true;

    /**
     * has the bottom border
     */
    private boolean hasBottom = true;

    public boolean isHasLeft() {
        return hasLeft;
    }

    public void setHasLeft(boolean hasLeft) {
        this.hasLeft = hasLeft;
    }

    public boolean isHasTop() {
        return hasTop;
    }

    public void setHasTop(boolean hasTop) {
        this.hasTop = hasTop;
    }

    public boolean isHasRight() {
        return hasRight;
    }

    public void setHasRight(boolean hasRight) {
        this.hasRight = hasRight;
    }

    public boolean isHasBottom() {
        return hasBottom;
    }

    public void setHasBottom(boolean hasBottom) {
        this.hasBottom = hasBottom;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        this.mWidth = width;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public PointF getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(PointF topLeft) {
        this.topLeft = topLeft;
    }

    public PointF getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(PointF bottomRight) {
        this.bottomRight = bottomRight;
    }
}
