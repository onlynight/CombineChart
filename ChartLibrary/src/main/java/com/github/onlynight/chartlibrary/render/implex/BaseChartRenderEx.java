package com.github.onlynight.chartlibrary.render.implex;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.OnCrossPointClickListener;
import com.github.onlynight.chartlibrary.render.IChartRender;

/**
 * Created by lion on 2017/9/11.
 */

public class BaseChartRenderEx implements IChartRender {
    @Override
    public float getScale() {
        return 0;
    }

    @Override
    public void setScale(float scale) {

    }

    @Override
    public float getXDelta() {
        return 0;
    }

    @Override
    public void setXDelta(float xDelta) {

    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return false;
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {

    }

    @Override
    public void setCrossPoint(PointF crossPoint) {

    }

    @Override
    public PointF getCrossPoint() {
        return null;
    }

    @Override
    public void setCrossColor(int crossColor) {

    }

    @Override
    public int getCrossBorderColor() {
        return 0;
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {

    }

    @Override
    public void setCrossBorderColor(int color) {

    }

    @Override
    public void onMeasure() {

    }

    @Override
    public void onDrawFrame(Canvas canvas) {

    }

    @Override
    public void onDrawChart(Canvas canvas) {

    }

    @Override
    public double getYMaxValue() {
        return 0;
    }

    @Override
    public double getYMinValue() {
        return 0;
    }

    @Override
    public void setIsClipContainer(boolean isClipContainer) {

    }

    @Override
    public void setOnCrossPointClickListener(OnCrossPointClickListener onCrossPointClickListener) {

    }

    @Override
    public boolean isClipContainer() {
        return false;
    }

    @Override
    public boolean isAutoZoomYAxis() {
        return false;
    }

    @Override
    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {

    }
}
