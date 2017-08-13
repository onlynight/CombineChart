package com.github.onlynight.chartlibrary.chart.part;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 * the chart's axis part
 */

public class Axis {

    /**
     * scale text gravity;
     * if text is vertical the top and bottom and center will effect,
     * or the text is horizontal the left and right and center will work.
     */
    public static final int TEXT_GRAVITY_CENTER = 1;
    public static final int TEXT_GRAVITY_LEFT = 2;
    public static final int TEXT_GRAVITY_RIGHT = 3;
    public static final int TEXT_GRAVITY_TOP = 4;
    public static final int TEXT_GRAVITY_BOTTOM = 5;

    /**
     * axis position,
     * you can set axis on the each side of the chart border.
     */
    public static final int POSITION_LEFT = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_RIGHT = 3;
    public static final int POSITION_BOTTOM = 4;

    /**
     * axis line type solid or dash.
     */
    public static final int AXIS_LINE_TYPE_SOLID = 0;
    public static final int AXIS_LINE_TYPE_DASH = 1;

    /**
     * axis line width
     */
    private float mWidth = 2f;

    /**
     * axis line color
     */
    private int mColor = Color.BLACK;

    /**
     * axis line type
     */
    private int mLineType = AXIS_LINE_TYPE_SOLID;

    /**
     * if the axis has the line
     */
    private boolean mHasLine = true;

    /**
     * scale text config
     */
    // scale text size
    private float mTextSize = 10;
    // scale text color
    private int mTextColor = Color.BLACK;
    // scale text gravity
    private int mTextGravity = TEXT_GRAVITY_CENTER;

    /**
     * is the scale has grid
     */
    private boolean mHasGrid = true;

    /**
     * grid size
     */
    private int mMaxGrid = 4; // 0 is null, more than 0 is specific value.

    /**
     * axis' position
     */
    private int mPosition;

    /**
     * is axis has scale text
     */
    private boolean mHasScaleText = true;

    /**
     * axis start position
     */
    private PointF mStartPos = new PointF();

    /**
     * axis end position
     */
    private PointF mEndPos = new PointF();

    /**
     * axis' scales
     */
    private List<Scale> mScales;

    public Axis() {
        mScales = new ArrayList<>();
    }

    public boolean isHasScaleText() {
        return mHasScaleText;
    }

    public void setHasScaleText(boolean hasScaleText) {
        this.mHasScaleText = hasScaleText;
    }

    public int getLineType() {
        return mLineType;
    }

    public void setLineType(int lineType) {
        this.mLineType = lineType;
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

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public boolean isHasGrid() {
        return mHasGrid;
    }

    public void setHasGrid(boolean hasGrid) {
        this.mHasGrid = hasGrid;
    }

    public int getMaxGrid() {
        return mMaxGrid;
    }

    public void setMaxGrid(int maxGrid) {
        this.mMaxGrid = maxGrid;
    }

    public int getTextGravity() {
        return mTextGravity;
    }

    public void setTextGravity(int textGravity) {
        this.mTextGravity = textGravity;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public boolean isHasLine() {
        return mHasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.mHasLine = hasLine;
    }

    public List<Scale> getScales() {
        return mScales;
    }

    public void setScales(List<Scale> scales) {
        this.mScales = scales;
    }

    public void addScale(Scale scale) {
        if (mScales == null) {
            mScales = new ArrayList<>();
        }
    }

    public PointF getStartPos() {
        return mStartPos;
    }

    public void setStartPos(PointF startPos) {
        this.mStartPos = startPos;
    }

    public PointF getEndPos() {
        return mEndPos;
    }

    public void setEndPos(PointF endPos) {
        this.mEndPos = endPos;
    }

}
