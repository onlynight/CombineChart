package com.github.onlynight.chartlibrary.chart.part;

import android.graphics.Color;

/**
 * Created by lion on 2017/8/10.
 */

public class Axis {

    public static final int TEXT_GRAVITY_CENTER = 1;
    public static final int TEXT_GRAVITY_LEFT = 2;
    public static final int TEXT_GRAVITY_RIGHT = 3;
    public static final int TEXT_GRAVITY_TOP = 4;
    public static final int TEXT_GRAVITY_BOTTOM = 5;

    public static final int POSITION_LEFT = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_RIGHT = 3;
    public static final int POSITION_BOTTOM = 4;

    private float mWidth = 2f;
    private int mColor = Color.BLACK;
    private boolean mHasLine = true;

    private float mTextSize = 10;
    private int mTextColor = Color.BLACK;
    private int mTextGravity = TEXT_GRAVITY_CENTER;

    private boolean mHasGrid = true;
    private int mMaxGrid = 4; // 0 is null, more than 0 is specific value.
    private int mPosition;

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
}
