package com.github.onlynight.chartlibrary.chart.part;

import android.graphics.PointF;

/**
 * Created by lion on 2017/8/12.
 * the chart's axis' scale part
 */

public class Scale {

    /**
     * scale text content
     */
    private String scaleText = "0.00";

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
     * has scale line
     */
    private boolean hasLine = true;

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

    public boolean isHasLine() {
        return hasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }
}
