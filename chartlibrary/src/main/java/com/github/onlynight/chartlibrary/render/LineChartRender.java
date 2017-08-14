package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public class LineChartRender extends BaseRender<LineChartData> {

    public LineChartRender(BaseChart chart) {
        super(chart);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (mChart.getDataList() != null) {
            for (Object data : mChart.getDataList()) {
                if (data instanceof LineChartData) {
                    drawLine(canvas, (LineChartData) data);
                }
            }
        }
    }

    private void drawLine(Canvas canvas, LineChartData data) {
        if (data == null ||
                data.getData() == null || data.getData().size() <= 0) {
            return;
        }

        List<LineEntity> entities = data.getData();
        Path path = new Path();
        PointF ptf = calculateChartPos(entities, 0);
        path.moveTo(ptf.x, ptf.y);
        for (int i = 0; i < entities.size(); i++) {
            PointF temp = calculateChartPos(entities, i);
            path.lineTo(temp.x, temp.y);
        }

        LineChartDataConfig config = data.getConfig();
        if (config != null) {
            mGraphPaint.setStrokeWidth(config.getStrokeWidth());
            mGraphPaint.setColor(config.getColor());
            mGraphPaint.setPathEffect(null);
        }

        canvas.drawPath(path, mGraphPaint);
    }

    private PointF calculateChartPos(List<LineEntity> entities, int index) {
        List<Scale> scales = mChart.getyAxis().getScales();
        if (scales == null || scales.size() <= 0) {
            return null;
        }

        float chartWidth = mChart.getxAxis().getEndPos().x -
                mChart.getxAxis().getStartPos().x;
        float chartHeight = scales.get(scales.size() - 1).getStartPos().y -
                scales.get(0).getStartPos().y;
        double maxValue = getYMaxValue();
        double minValue = getYMinValue();
        double valueRange = maxValue - minValue;

        float blank = chartWidth / (entities.size() - 1);

        LineEntity entity = entities.get(index);
        float y = (float) ((entity.getY() - minValue) / valueRange * chartHeight)
                + scales.get(0).getStartPos().y;
        float x;
        switch (mChart.getyAxis().getPosition()) {
            case Axis.POSITION_RIGHT:
                x = mChart.getxAxis().getEndPos().x -
                        blank * (entities.size() - index - 1) * mScale;
                break;
            case Axis.POSITION_LEFT:
            default:
                x = blank * index * mScale + mChart.getxAxis().getStartPos().x;
                break;
        }
        x += xDelta;

        return new PointF(x, y);
    }

}
