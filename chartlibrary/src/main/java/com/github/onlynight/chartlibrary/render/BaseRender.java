package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseRender<T extends BaseChartData> {

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected BaseChart mChart;
    protected Path mContainerPath;

    protected float mScale = 1f;
    protected float xDelta = 0;

    public BaseRender(BaseChart chart) {
        this.mChart = chart;
        this.mContainerPath = new Path();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureBorder();
        measureAxis();
        measureAxisScale();
    }

    public void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
        drawAxisScale(canvas);
    }

    public void onDrawChart(Canvas canvas) {
    }

    protected void measureBorder() {
        float halfBorder = mChart.getBorder().getWidth() / 2;
        PointF topLeft = new PointF(mChart.getLeft() + halfBorder,
                mChart.getTop() + halfBorder);
        PointF bottomRight = new PointF(mChart.getRight() - halfBorder, mChart.getBottom() - halfBorder);
        mChart.getBorder().setTopLeft(topLeft);
        mChart.getBorder().setBottomRight(bottomRight);
    }

    protected void measureAxis() {
        measureYAxis();
        measureXAxis();
    }

    protected void measureYAxis() {
        mTextPaint.setTextSize(mChart.getyAxis().getTextSize());
        mGraphPaint.setStrokeWidth(mChart.getyAxis().getWidth());

        String max = getYAxisTextMaxLength(mChart.getDataList());
        float scaleMaxWidth = mTextPaint.measureText(max);

        float x = 0;

        switch (mChart.getyAxis().getPosition()) {
            case Axis.POSITION_RIGHT:
                if (mChart.getyAxis().isHasScaleText()) {
                    x = mChart.getRight() - (scaleMaxWidth + BaseChart.getBLANK() * 2);
                } else {
                    x = mChart.getRight();
                }
                mChart.getxAxis().getStartPos().x = 0;
                mChart.getxAxis().getEndPos().x = x;
                break;
            case Axis.POSITION_LEFT:
            default:
                if (mChart.getyAxis().isHasScaleText()) {
                    x = mChart.getLeft() + scaleMaxWidth + BaseChart.getBLANK() * 2;
                } else {
                    x = mChart.getLeft();
                }
                mChart.getxAxis().getStartPos().x = x;
                mChart.getxAxis().getEndPos().x = mChart.getRight()
                        - mChart.getBorder().getWidth() / 2;
                break;
        }

        mChart.getyAxis().getStartPos().x = x;
        mChart.getyAxis().getEndPos().x = x;
    }

    protected void measureXAxis() {
        mTextPaint.setTextSize(mChart.getxAxis().getTextSize());
        mGraphPaint.setStrokeWidth(mChart.getxAxis().getWidth());

        float fontHeight = getFontHeight(mTextPaint);

        float y = 0;

        switch (mChart.getxAxis().getPosition()) {
            case Axis.POSITION_BOTTOM:
                if (mChart.getxAxis().isHasScaleText()) {
                    y = mChart.getBottom() - (fontHeight + BaseChart.getBLANK() * 2);
                } else {
                    y = mChart.getBottom();
                }
                mChart.getyAxis().getStartPos().y = mChart.getTop() +
                        mChart.getBorder().getWidth() / 2;
                mChart.getyAxis().getEndPos().y = y;
            default:
                break;
            case Axis.POSITION_TOP:
                if (mChart.getxAxis().isHasScaleText()) {
                    y = mChart.getTop() + fontHeight + BaseChart.getBLANK() * 2;
                } else {
                    y = mChart.getTop();
                }
                mChart.getyAxis().getStartPos().y = y;
                mChart.getyAxis().getEndPos().y = mChart.getBottom() -
                        mChart.getBorder().getWidth() / 2;
                break;
        }

        mChart.getxAxis().getStartPos().y = y;
        mChart.getxAxis().getEndPos().y = y;
    }

    private String getYAxisTextMaxLength(List<T> dataList) {
        if (dataList == null) {
            return "";
        }

        String max = "";
        for (T data : dataList) {
            String temp = getYAxisTextMaxLength(data);
            if (temp.length() > max.length()) {
                max = temp;
            }
        }

        return max;
    }

    private String getYAxisTextMaxLength(T chartData) {
        if (chartData == null) {
            return "";
        }

        String max = "";
        for (Object temp : chartData.getData()) {
            if (temp instanceof BaseEntity) {
                BaseEntity entity = (BaseEntity) temp;
                if (!TextUtils.isEmpty(entity.getyValue()) &&
                        entity.getyValue().length() > max.length()) {
                    max = entity.getyValue();
                }
            }
        }

        return max;
    }

    /**
     * @return get font height
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }

    protected void measureAxisScale() {
        measureYAxisScale();
        measureXAxisScale();
    }

    private void measureYAxisScale() {
        int grid = mChart.getyAxis().getGrid();
        float yAxisHeight;
        if (mChart.getMarginTextSize() > 0) {
            yAxisHeight = mChart.getyAxis().getEndPos().y -
                    mChart.getyAxis().getStartPos().y
                    - mChart.getMarginTextSize() * 2 - BaseChart.getBLANK() * 2;
        } else {
            yAxisHeight = mChart.getyAxis().getEndPos().y -
                    mChart.getyAxis().getStartPos().y;
        }

        float blank;
        double scaleValueBlank;
        double minYValue = getYMinValue();
        double maxYValue = getYMaxValue();

        if (grid > 0) {
            blank = yAxisHeight / grid;
            scaleValueBlank = (maxYValue - minYValue) / grid;
        } else {
            blank = yAxisHeight;
            scaleValueBlank = maxYValue - minYValue;
        }

        blank = Math.abs(blank);

        List<Scale> scales = mChart.getyAxis().getScales();
        scales.clear();

        for (int i = 0; i <= grid; i++) {
            Scale scale = new Scale();
            PointF startPt = new PointF();
            PointF endPt = new PointF();
            float y;
            if (mChart.getMarginTextSize() > 0) {
                y = i * blank + mChart.getMarginTextSize() +
                        BaseChart.getBLANK() + mChart.getyAxis().getStartPos().y;
            } else {
                y = i * blank + mChart.getyAxis().getStartPos().y;
            }
            startPt.x = mChart.getxAxis().getStartPos().x;
            startPt.y = y;
            endPt.x = mChart.getxAxis().getEndPos().x;
            endPt.y = y;

            scale.setStartPos(startPt);
            scale.setEndPos(endPt);

            if (mChart.getMarginTextSize() <= 0) {
                if (i == 0 || i == grid) {
                    scale.setHasLine(false);
                }
            }

            // calculate scale text position
            if (mChart.getyAxis().isHasScaleText()) {
                mTextPaint.setTextSize(mChart.getyAxis().getTextSize());
                float fontHeight = getFontHeight(mTextPaint);

                switch (mChart.getyAxis().getPosition()) {
                    case Axis.POSITION_LEFT:
                    default: {
                        PointF scalePos = new PointF();
                        scalePos.x = mChart.getLeft() + BaseChart.getBLANK();
                        scalePos.y = startPt.y;

                        switch (mChart.getyAxis().getTextGravity()) {
                            case Axis.TEXT_GRAVITY_CENTER:
                                scalePos.y += fontHeight / 2;
                                break;
                            case Axis.TEXT_GRAVITY_BOTTOM:
                                scalePos.y += fontHeight;
                                break;
                        }
                        scale.setScaleTextPos(scalePos);
                    }
                    break;

                    case Axis.POSITION_RIGHT: {
                        PointF scalePos = new PointF();
                        scalePos.x = endPt.x + BaseChart.getBLANK();
                        scalePos.y = endPt.y;

                        switch (mChart.getyAxis().getTextGravity()) {
                            case Axis.TEXT_GRAVITY_CENTER:
                                scalePos.y += fontHeight / 2;
                                break;
                            case Axis.TEXT_GRAVITY_BOTTOM:
                                scalePos.y += fontHeight;
                                break;
                        }
                        scale.setScaleTextPos(scalePos);
                    }
                    break;
                }
            }

            // set scale text
            try {
                if (mChart.getDataList() != null && mChart.getDataList().size() > 0) {
                    Object obj = mChart.getDataList().get(0);
                    if (obj instanceof BaseChartData) {
                        BaseChartData temp = (BaseChartData) obj;
                        DecimalFormat df = new DecimalFormat(temp.getConfig().getYFormat());
                        scale.setScaleText(df.format(maxYValue - i * scaleValueBlank));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mChart.getyAxis().addScale(scale);
        }
    }

    private void measureXAxisScale() {
        int grid = mChart.getxAxis().getGrid();
        float width = mChart.getxAxis().getEndPos().x - mChart.getxAxis().getStartPos().x;
        float blank = width / grid;

        List<Scale> scales = mChart.getxAxis().getScales();
        scales.clear();
        for (int i = 1; i < grid; i++) {
            PointF startPos = new PointF();
            PointF endPos = new PointF();

            startPos.x = mChart.getxAxis().getStartPos().x + i * blank;
            endPos.x = mChart.getxAxis().getStartPos().x + i * blank;

            if (mChart.getMarginTextSize() > 0) {
                startPos.y = mChart.getyAxis().getStartPos().y +
                        mChart.getMarginTextSize() + BaseChart.getBLANK() * 2;
                endPos.y = mChart.getyAxis().getEndPos().y -
                        (mChart.getMarginTextSize() + BaseChart.getBLANK() * 2);
            } else {
                startPos.y = mChart.getyAxis().getStartPos().y;
                endPos.y = mChart.getyAxis().getEndPos().y;
            }

            Scale scale = new Scale();
            scale.setStartPos(startPos);
            scale.setEndPos(endPos);

            // add scale text
            if (mChart.getDataList() != null && mChart.getDataList().size() > 0) {
                Object temp1 = mChart.getDataList().get(0);
                if (temp1 instanceof BaseChartData) {
                    BaseChartData dataZero = (BaseChartData) temp1;
                    if (dataZero.getData() != null &&
                            dataZero.getData().size() >= grid) {
                        Object temp = dataZero.getData().get(
                                dataZero.getData().size() / grid * i);
                        if (temp instanceof BaseEntity) {
                            scale.setScaleText(((BaseEntity) temp).getxValue());
                        }
                    }
                }

            }

            // calculate scale text position
            if (mChart.getxAxis().isHasScaleText()) {
                mTextPaint.setTextSize(mChart.getxAxis().getTextSize());
                float fontHeight = getFontHeight(mTextPaint);
                float textWidth = mTextPaint.measureText(scale.getScaleText());

                switch (mChart.getxAxis().getPosition()) {
                    case Axis.POSITION_TOP:
                    default: {
                        PointF scalePos = new PointF();
                        scalePos.x = startPos.x;
                        scalePos.y = startPos.y - BaseChart.getBLANK();

                        switch (mChart.getxAxis().getTextGravity()) {
                            case Axis.TEXT_GRAVITY_CENTER:
                                scalePos.x -= (textWidth / 2);
                                break;
                            case Axis.TEXT_GRAVITY_LEFT:
                                scalePos.x -= textWidth;
                                break;
                        }
                        scale.setScaleTextPos(scalePos);
                    }
                    break;

                    case Axis.POSITION_BOTTOM: {
                        PointF scalePos = new PointF();
                        scalePos.x = endPos.x;
                        scalePos.y = endPos.y + fontHeight + BaseChart.getBLANK();

                        if (mChart.getMarginTextSize() > 0) {
                            scalePos.y += mChart.getMarginTextSize()
                                    + BaseChart.getBLANK() * 2;
                        }

                        switch (mChart.getxAxis().getTextGravity()) {
                            case Axis.TEXT_GRAVITY_CENTER:
                                scalePos.x -= (textWidth / 2);
                                break;
                            case Axis.TEXT_GRAVITY_LEFT:
                                scalePos.x -= textWidth;
                                break;
                        }

                        scale.setScaleTextPos(scalePos);
                    }
                    break;
                }
            }

            mChart.getxAxis().addScale(scale);
        }
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

    private void drawBorder(Canvas canvas) {
        mGraphPaint.setColor(mChart.getBorder().getColor());
        mGraphPaint.setStrokeWidth(mChart.getBorder().getWidth());
        mGraphPaint.setStyle(Paint.Style.STROKE);

        if (mChart.getBorder().isHasLeft()) {
            canvas.drawLine(mChart.getBorder().getTopLeft().x,
                    mChart.getBorder().getTopLeft().y,
                    mChart.getBorder().getTopLeft().x,
                    mChart.getBorder().getBottomRight().y, mGraphPaint);
        }

        if (mChart.getBorder().isHasTop()) {
            canvas.drawLine(mChart.getBorder().getTopLeft().x,
                    mChart.getBorder().getTopLeft().y,
                    mChart.getBorder().getBottomRight().x,
                    mChart.getBorder().getTopLeft().y, mGraphPaint);
        }

        if (mChart.getBorder().isHasRight()) {
            canvas.drawLine(mChart.getBorder().getBottomRight().x,
                    mChart.getBorder().getTopLeft().y,
                    mChart.getBorder().getBottomRight().x,
                    mChart.getBorder().getBottomRight().y, mGraphPaint);
        }

        if (mChart.getBorder().isHasBottom()) {
            canvas.drawLine(mChart.getBorder().getTopLeft().x,
                    mChart.getBorder().getBottomRight().y,
                    mChart.getBorder().getBottomRight().x,
                    mChart.getBorder().getBottomRight().y, mGraphPaint);
        }
    }

    private void drawAxis(Canvas canvas) {

        // draw axis y
        if (mChart.getyAxis().isHasLine()) {
            mGraphPaint.setStrokeWidth(mChart.getyAxis().getWidth());
            mGraphPaint.setColor(mChart.getyAxis().getColor());
            mGraphPaint.setStyle(Paint.Style.STROKE);

            if (mChart.getyAxis().getLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            canvas.drawLine(mChart.getyAxis().getStartPos().x,
                    mChart.getyAxis().getStartPos().y,
                    mChart.getyAxis().getEndPos().x,
                    mChart.getyAxis().getEndPos().y, mGraphPaint);
        }

        // draw axis x
        if (mChart.getxAxis().isHasLine()) {
            mGraphPaint.setStrokeWidth(mChart.getxAxis().getWidth());
            mGraphPaint.setColor(mChart.getxAxis().getColor());
            mGraphPaint.setStyle(Paint.Style.STROKE);

            if (mChart.getxAxis().getLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            canvas.drawLine(mChart.getxAxis().getStartPos().x,
                    mChart.getxAxis().getStartPos().y,
                    mChart.getxAxis().getEndPos().x,
                    mChart.getxAxis().getEndPos().y, mGraphPaint);
        }
    }

    private void drawAxisScale(Canvas canvas) {

        if (mChart.getyAxis().isHasScaleLine()) {
            mGraphPaint.setStrokeWidth(mChart.getyAxis().getScaleLineWidth());
            mGraphPaint.setColor(mChart.getyAxis().getScaleLineColor());
            mTextPaint.setTextSize(mChart.getyAxis().getTextSize());
            mTextPaint.setColor(mChart.getyAxis().getTextColor());

            if (mChart.getyAxis().getScaleLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            for (Scale scale : mChart.getyAxis().getScales()) {
                if (scale.isHasLine()) {
                    Path path = new Path();
                    path.moveTo(scale.getStartPos().x, scale.getStartPos().y);
                    path.lineTo(scale.getEndPos().x, scale.getEndPos().y);
                    canvas.drawPath(path, mGraphPaint);
                }

                if (mChart.getyAxis().isHasScaleText()) {
                    canvas.drawText(scale.getScaleText(), scale.getScaleTextPos().x,
                            scale.getScaleTextPos().y, mTextPaint);
                }
            }
        }

        if (mChart.getxAxis().isHasScaleLine()) {
            mGraphPaint.setStrokeWidth(mChart.getxAxis().getScaleLineWidth());
            mGraphPaint.setColor(mChart.getxAxis().getScaleLineColor());

            if (mChart.getxAxis().getScaleLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            boolean hasScaleText = mChart.getxAxis().isHasScaleText();

            for (Scale scale : mChart.getxAxis().getScales()) {
                Path path = new Path();
                path.moveTo(scale.getStartPos().x, scale.getStartPos().y);
                path.lineTo(scale.getEndPos().x, scale.getEndPos().y);
                canvas.drawPath(path, mGraphPaint);

                if (hasScaleText) {
                    canvas.drawText(scale.getScaleText(), scale.getScaleTextPos().x,
                            scale.getScaleTextPos().y, mTextPaint);
                }
            }
        }
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float mScale) {
        this.mScale = mScale;
    }

    public float getxDelta() {
        return xDelta;
    }

    public void setxDelta(float xDelta) {
        this.xDelta = xDelta;
    }
}
