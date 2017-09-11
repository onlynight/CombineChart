package com.github.onlynight.chartlibrary.render.impl.part;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.impl.BaseChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.render.part.IPartRender;

/**
 * Created by lion on 2017/8/22.
 */

public abstract class BasePartRender implements IPartRender {

    static final int DEFAULT_BAR_WIDTH = 2;

    BaseChart mChart;

    Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    float mScale = 1f;
    float mXDelta = 0;

    private PointF mCrossPoint;
    private int mCrossColor = Color.GRAY;
    private float mCrossLineWidth = 2f;
    private int mCrossBorderColor = Color.GRAY;

    protected boolean isCanZoomLessThanNormal = true;

    BasePartRender(BaseChart chart) {
        this.mChart = chart;
        this.mCrossPoint = new PointF(-1, -1);
    }

    @Override
    public void onMeasure() {
        measureCandleChartItemWidth();
    }

    private void measureCandleChartItemWidth() {
        if (mChart != null && mChart.getDataList() != null) {
            for (Object temp : mChart.getDataList()) {
                if (temp instanceof BaseChartData) {
                    BaseChartData data = (BaseChartData) temp;
                    BaseChartDataConfig config = data.getConfig();
                    float chartWidth = mChart.getXAxis().getEndPos().x -
                            mChart.getXAxis().getStartPos().x;
                    if (config.isAutoWidth()) {
                        if (data.getData() != null &&
                                data.getData().size() > 0) {
                            config.setBarWidth(chartWidth / data.getData().size());
                        }
                    } else {
                        float tempWidth = data.getData().size() * config.getBarWidth();
                        if (tempWidth < mChart.getXAxis().getEndPos().x -
                                mChart.getXAxis().getStartPos().x &&
                                data.getData().size() > 0) {
                            isCanZoomLessThanNormal = false;
                            config.setBarWidth(chartWidth / data.getData().size());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setXDelta(float xDelta) {
        this.mXDelta = xDelta;
    }

    @Override
    public float getXDelta() {
        return mXDelta;
    }

    @Override
    public void setScale(float scale) {
        this.mScale = scale;
    }

    @Override
    public float getScale() {
        return mScale;
    }

    double getYMaxValue() {
        double max = Double.MIN_VALUE;
        for (Object obj : mChart.getDataList()) {
            if (obj instanceof BaseChartData) {
                double temp = ((BaseChartData) obj).getYMax();
                if (temp > max) {
                    max = temp;
                }
            }
        }

        return max;
    }

    double getYMinValue() {
        double min = Double.MAX_VALUE;
        for (Object obj : mChart.getDataList()) {
            if (obj instanceof BaseChartData) {
                double temp = ((BaseChartData) obj).getYMin();
                if (temp < min) {
                    min = temp;
                }
            }
        }

        return min;
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return isCanZoomLessThanNormal;
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        isCanZoomLessThanNormal = canZoomLessThanNormal;
    }

    @Override
    public void setCrossPoint(PointF crossPoint) {
        this.mCrossPoint = crossPoint;
    }

    public PointF getCrossPoint() {
        return mCrossPoint;
    }

    @Override
    public void setCrossColor(int crossColor) {
        mCrossColor = crossColor;
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        mCrossLineWidth = crossLineWidth;
    }

    @Override
    public void setCrossBorderColor(int color) {
        mCrossBorderColor = color;
    }

    public int getCrossBorderColor() {
        return mCrossBorderColor;
    }

    /**
     * @return get font height
     */
    float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }

    void drawCross(Canvas canvas) {
        if (isInChartRange(mCrossPoint)) {
            mGraphPaint.setColor(mCrossColor);
            mGraphPaint.setStrokeWidth(mCrossLineWidth);
            mGraphPaint.setStyle(Paint.Style.STROKE);

            canvas.drawLine(0, mCrossPoint.y, mChart.getWidth(), mCrossPoint.y, mGraphPaint);
            canvas.drawLine(mCrossPoint.x, mChart.getTop(), mCrossPoint.x, mChart.getTop() + mChart.getHeight(), mGraphPaint);
        }

    }

    private boolean isInChartYRange(PointF point) {
        float top = mChart.getYAxis().getScales().get(0).getStartPos().y;
        float bottom = mChart.getYAxis().getScales().get(mChart.getYAxis().getScales().size() - 1).getStartPos().y;

        return point.y > top &&
                point.y < bottom;
    }

    private boolean isInChartXRange(PointF point) {
        float left = mChart.getXAxis().getStartPos().x;
        float right = mChart.getYAxis().getEndPos().x;

        return point.x > left &&
                point.x < right;
    }

    private boolean isInChartRange(PointF point) {
        return isInChartXRange(point);
    }

}
