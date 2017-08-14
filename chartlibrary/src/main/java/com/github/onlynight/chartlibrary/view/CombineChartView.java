package com.github.onlynight.chartlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.onlynight.chartlibrary.chart.BaseChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public class CombineChartView extends View {

    private List<BaseChart> mCharts;
    private float mScale = 1f;
    private float xDelta = 0;

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
    }

    public void addChart(BaseChart chart) {
        mCharts.add(chart);
    }

    private List<Double> mChartsHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        double total = 0;
        mChartsHeight.clear();
        for (BaseChart chart : mCharts) {
            total += chart.getWeight();
        }

        if (total > 0) {
            int height = getHeight();
            for (BaseChart chart : mCharts) {
                mChartsHeight.add(chart.getWeight() / total * height);
            }

            for (int i = 0; i < mCharts.size(); i++) {
                double tempTop = sum(mChartsHeight, i);
                mCharts.get(i).setArea(0, (int) tempTop,
                        getWidth(), (int) (mChartsHeight.get(i).intValue() + tempTop));
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (BaseChart chart : mCharts) {
            chart.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (BaseChart chart : mCharts) {
            chart.onDraw(canvas);
        }
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float mScale) {
        this.mScale = mScale;
        for (BaseChart chart : mCharts) {
            chart.setScale(mScale);
        }
    }

    public float getxDelta() {
        return xDelta;
    }

    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
        for (BaseChart chart : mCharts) {
            chart.setxDelta(xDelta);
        }
    }
}
