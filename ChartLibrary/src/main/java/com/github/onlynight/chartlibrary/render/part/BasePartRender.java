package com.github.onlynight.chartlibrary.render.part;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.operate.IChartInterface;

/**
 * Created by lion on 2017/8/22.
 */

public abstract class BasePartRender implements IChartInterface {

    public static final int DEFAULT_BAR_WIDTH = 2;

    protected BaseChart mChart;

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected float mScale = 1f;
    protected float mXDelta = 0;

    protected PointF mCrossPoint;
    private int mCrossColor = Color.GRAY;
    private float mCrossLineWidth = 2f;
    private int mCrossBorderColor = Color.GRAY;

    protected boolean isCanZoomLessThanNormal = true;

    public BasePartRender(BaseChart chart) {
        this.mChart = chart;
        this.mCrossPoint = new PointF(-1, -1);
    }

    public void onMeasure() {
        measureCandleChartItemWidth();
    }

    public abstract void onDrawChart(Canvas canvas);

    protected void measureCandleChartItemWidth() {
        if (mChart != null && mChart.getDataList() != null) {
            for (Object temp : mChart.getDataList()) {
                if (temp instanceof BaseChartData) {
                    BaseChartData data = (BaseChartData) temp;
                    BaseChartDataConfig config = data.getConfig();
                    float chartWidth = mChart.getxAxis().getEndPos().x -
                            mChart.getxAxis().getStartPos().x;
                    if (config.isAutoWidth()) {
                        if (data.getData() != null &&
                                data.getData().size() > 0) {
                            config.setBarWidth(chartWidth / data.getData().size());
                        }
                    } else {
                        float tempWidth = data.getData().size() * config.getBarWidth();
                        if (tempWidth < mChart.getxAxis().getEndPos().x -
                                mChart.getxAxis().getStartPos().x &&
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
    public void setxDelta(float xDelta) {
        this.mXDelta = xDelta;
    }

    @Override
    public float getxDelta() {
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

    protected double getYMaxValue() {
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

    protected double getYMinValue() {
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
    protected float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }

    protected void drawCross(Canvas canvas) {
        if (isInChartRange(mCrossPoint)) {
            mGraphPaint.setColor(mCrossColor);
            mGraphPaint.setStrokeWidth(mCrossLineWidth);
            mGraphPaint.setStyle(Paint.Style.STROKE);

            canvas.drawLine(0, mCrossPoint.y, mChart.getWidth(), mCrossPoint.y, mGraphPaint);
            canvas.drawLine(mCrossPoint.x, mChart.getTop(), mCrossPoint.x, mChart.getTop() + mChart.getHeight(), mGraphPaint);
        }

    }

    public boolean isInChartYRange(PointF point) {
        float top = mChart.getyAxis().getScales().get(0).getStartPos().y;
        float bottom = mChart.getyAxis().getScales().get(mChart.getyAxis().getScales().size() - 1).getStartPos().y;

        return point.y > top &&
                point.y < bottom;
    }

    public boolean isInChartXRange(PointF point) {
        float left = mChart.getxAxis().getStartPos().x;
        float right = mChart.getyAxis().getEndPos().x;

        return point.x > left &&
                point.x < right;
    }

    public boolean isInChartRange(PointF point) {
        return isInChartXRange(point);
    }

}
