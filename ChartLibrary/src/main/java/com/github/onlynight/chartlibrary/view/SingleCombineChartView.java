package com.github.onlynight.chartlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.operate.IChartInterface;

/**
 * Created by lion on 2017/8/10.
 */

public class SingleCombineChartView extends View implements IChartInterface {

    private BaseChart mChart;
    private float mScale = 1f;
    private float xDelta = 0;

    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    private MyGestureListener gestureListener;

    private boolean mIsOperatable = true;

    public SingleCombineChartView(Context context) {
        super(context);
        initView();
    }

    public SingleCombineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SingleCombineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        gestureListener = new MyGestureListener();
        mDetector = new GestureDetectorCompat(getContext(), gestureListener);
        mScaleDetector = new ScaleGestureDetector(getContext(), gestureListener);
    }

    public void setChart(BaseChart chart) {
        mChart = chart;
    }

    private void onLayoutChart(int width, int height) {
        mChart.setArea(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onLayoutChart(getWidth(), getHeight());
        mChart.onMeasure();
        mChart.onDraw(canvas);
        mChart.onDrawChart(canvas);
    }

    @Override
    public float getScale() {
        return mScale;
    }

    @Override
    public void setScale(float scale) {
        this.mScale = scale;
        mChart.setScale(scale);
    }

    @Override
    public float getxDelta() {
        return xDelta;
    }

    @Override
    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
        mChart.setxDelta(xDelta);
    }

    @Deprecated
    @Override
    public boolean isCanZoomLessThanNormal() {
        // do nothing
        return false;
    }

    @Deprecated
    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        // do nothing
    }

    @Deprecated
    @Override
    public void setCrossPoint(PointF crossPoint) {
        // do nothing
    }

    @Deprecated
    @Override
    public void setCrossColor(int crossColor) {
        // do nothing
    }

    @Deprecated
    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        // do nothing
    }

    @Deprecated
    @Override
    public void setCrossBorderColor(int color) {
        // do nothing
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsOperatable) {
            int count = event.getPointerCount();
            if (count <= 1) {
                return mDetector.onTouchEvent(event);
            } else {
                return mScaleDetector.onTouchEvent(event);
            }
        } else {
            return super.onTouchEvent(event);
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
            implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            e.getAction();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            setxDelta(getxDelta() - distanceX);
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float currentScale = detector.getScaleFactor();
            float scale = mLastScale * currentScale;
            setScale(scale);
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mLastScale = getScale();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    private float mLastScale = 1f;

    public boolean isOperatable() {
        return mIsOperatable;
    }

    public void setOperatable(boolean operatable) {
        mIsOperatable = operatable;
    }
}
