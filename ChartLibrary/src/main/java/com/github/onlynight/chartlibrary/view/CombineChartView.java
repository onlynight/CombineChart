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

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.chart.IChartOperate;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public class CombineChartView extends View implements IChartView, IChartOperate {

    private List<IChart> mCharts;
    private float mScale = 1f;
    private float xDelta = 0;

    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    private MyGestureListener mGestureListener;
    private List<Double> mChartsHeight = new ArrayList<>();

    private boolean mCanOperate = true;
    private boolean mIsShowCrossPoint = true;

    private float mLastScale = 1f;
    private PointF mTouchPoint;

    public CombineChartView(Context context) {
        super(context);
        initView();
    }

    public CombineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CombineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mCharts = new ArrayList<>();
        mGestureListener = new MyGestureListener();
        mDetector = new GestureDetectorCompat(getContext(), mGestureListener);
        mScaleDetector = new ScaleGestureDetector(getContext(), mGestureListener);

        mTouchPoint = new PointF(-1, -1);
    }

    public void addChart(IChart chart) {
        if (chart != null) {
            chart.setIsClipContainer(true);
            mCharts.add(chart);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        onLayoutChart(getWidth(), getHeight());
    }

    private void onLayoutChart(int width, int height) {
        double total = 0;
        mChartsHeight.clear();
        for (IChart chart : mCharts) {
            total += chart.getWeight();
        }

        if (total > 0) {
//            int height = getHeight();
            for (IChart chart : mCharts) {
                mChartsHeight.add(chart.getWeight() / total * height);
            }

            for (int i = 0; i < mCharts.size(); i++) {
                double tempTop = sum(mChartsHeight, i);
                mCharts.get(i).setArea(0, (int) tempTop,
                        width, (int) (mChartsHeight.get(i).intValue() + tempTop));
            }
        }
    }

    private double sum(List<Double> heights, int index) {
        if (index < 0) {
            return 0;
        }

        double total = 0;

        for (int i = 0; i < index; i++) {
            total += heights.get(i);
        }

        return total;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (IChart chart : mCharts) {
            chart.onMeasure();
        }

        for (IChart chart : mCharts) {
            chart.onDrawFrame(canvas);
        }

        for (IChart chart : mCharts) {
            chart.onDrawChart(canvas);
        }
    }

    @Override
    public float getScale() {
        return mScale;
    }

    @Override
    public void setScale(float scale) {
        if (scale >= 5) {
            scale = 5;
        }

        if (scale <= 0.5) {
            scale = 0.5f;
        }

        if (mCharts != null && mCharts.size() > 0) {
            if (!mCharts.get(0).isCanZoomLessThanNormal()) {
                if (scale <= 1) {
                    scale = 1;
                }
            }
        }

        this.mScale = scale;

        if (mCharts != null) {
            for (IChart chart : mCharts) {
                chart.setScale(this.mScale);
            }
        }
    }

    @Override
    public float getXDelta() {
        return xDelta;
    }

    @Override
    public void setXDelta(float xDelta) {
        if (xDelta <= 0) {
            xDelta = 0;
        }

        int max = calculateMax();

        if (xDelta >= max) {
            xDelta = max;
        }

        this.xDelta = xDelta;
        for (IChart chart : mCharts) {
            chart.setXDelta(this.xDelta);
        }
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        // do nothing
        return false;
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        // do nothing
    }

    @Override
    public void setCrossPoint(PointF crossPoint) {
        if (mCharts != null && mIsShowCrossPoint) {
            for (IChart chart : mCharts) {
                chart.setCrossPoint(crossPoint);
            }
        }
    }

    @Override
    public PointF getCrossPoint() {
        try {
            return mCharts.get(0).getCrossPoint();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int calculateMax() {
        try {
            IChart chart = mCharts.get(0);
            BaseChartData chartData = (BaseChartData) chart.getDataList().get(0);
            BaseChartDataConfig config = chartData.getConfig();
            int size = chartData.getData().size();
            return (int) (config.getBarWidth() * size * mScale - chart.getXAxis().getEndPos().x
                    + chart.getXAxis().getStartPos().x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCanOperate) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setXDelta(getXDelta());
                invalidate();
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mTouchPoint.set(event.getX(), event.getY());
                setCrossPoint(mTouchPoint);
            }
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

    public void clearCharts() {
        if (mCharts != null) {
            mCharts.clear();
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
            implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTouchPoint.set(-1, -1);
            setCrossPoint(mTouchPoint);
            setXDelta(getXDelta() - distanceX);
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            setXDelta(getXDelta() + velocityX / 10);
//            invalidate();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mTouchPoint.set(-1, -1);
            float currentScale = detector.getScaleFactor();
            float scale = mLastScale * currentScale;
            setScale(scale);
            setXDelta(getXDelta());
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

    @Override
    public boolean canOperate() {
        return mCanOperate;
    }

    public void setCanOperate(boolean canOperate) {
        mCanOperate = canOperate;
    }

    @Override
    public void setCrossColor(int crossColor) {
        if (mCharts != null) {
            for (IChart chart : mCharts) {
                chart.setCrossColor(crossColor);
            }
        }
    }

    @Override
    public int getCrossBorderColor() {
        return 0;
    }

    @Override
    public boolean isShowCrossPoint() {
        return mIsShowCrossPoint;
    }

    @Override
    public void setIsShowCrossPoint(boolean isShowCrossPoint) {
        this.mIsShowCrossPoint = isShowCrossPoint;
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        if (mCharts != null) {
            for (IChart chart : mCharts) {
                chart.setCrossLineWidth(crossLineWidth);
            }
        }
    }

    @Override
    public void setCrossBorderColor(int color) {
        if (mCharts != null) {
            for (IChart chart : mCharts) {
                chart.setCrossBorderColor(color);
            }
        }
    }

    public void firstInvalidate() {
        requestLayout();
        invalidate();
    }
}
