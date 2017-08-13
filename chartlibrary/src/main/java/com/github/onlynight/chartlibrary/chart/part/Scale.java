package com.github.onlynight.chartlibrary.chart.part;

import android.graphics.PointF;

/**
 * Created by lion on 2017/8/12.
 * the chart's axis' scale part
 */

public class Scale {

    /**
     * scale vertical line type
     */
    public static final int SCALE_LINE_TYPE_SOLID = 0;
    public static final int SCALE_LINE_TYPE_DASH = 1;

    /**
     * scale text content
     */
    private String scaleText;

    /**
     * scale text draw position
     */
    private PointF scaleTextPos;

    /**
     * scale vertical line start position
     */
    private PointF startPos;

    /**
     * scale vertical line end position
     */
    private PointF endPos;

    /**
     * scale line width
     */
    private float scaleLineWidth = 1;

    /**
     * scale line type
     */
    private int scaleLineType = SCALE_LINE_TYPE_SOLID;

    /**
     * has scale line
     */
    private boolean hasLine = true;

    public boolean isHasLine() {
        return hasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }

    public float getScaleLineWidth() {
        return scaleLineWidth;
    }

    public void setScaleLineWidth(float scaleLineWidth) {
        this.scaleLineWidth = scaleLineWidth;
    }

    public int getScaleLineType() {
        return scaleLineType;
    }

    public void setScaleLineType(int scaleLineType) {
        this.scaleLineType = scaleLineType;
    }

    public String getScaleText() {
        return scaleText;
    }

    public void setScaleText(String scaleText) {
        this.scaleText = scaleText;
    }

    public PointF getScaleTextPos() {
        return scaleTextPos;
    }

    public void setScaleTextPos(PointF scaleTextPos) {
        this.scaleTextPos = scaleTextPos;
    }

    public PointF getStartPos() {
        return startPos;
    }

    public void setStartPos(PointF startPos) {
        this.startPos = startPos;
    }

    public PointF getEndPos() {
        return endPos;
    }

    public void setEndPos(PointF endPos) {
        this.endPos = endPos;
    }
}
