package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseRender {

    protected int mLeft;
    protected int mTop;
    protected int mRight;
    protected int mBottom;

    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public void onDraw(Canvas canvas) {
    }

    public void setArea(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
    }

}
