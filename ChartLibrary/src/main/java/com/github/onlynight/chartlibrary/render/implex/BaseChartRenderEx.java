package com.github.onlynight.chartlibrary.render.implex;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.chart.IChart;
import com.github.onlynight.chartlibrary.chart.OnCrossPointClickListener;
import com.github.onlynight.chartlibrary.chart.impl.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.render.IChartRender;
import com.github.onlynight.chartlibrary.render.part.IPartRender;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lion on 2017/9/11.
 */

public class BaseChartRenderEx<Data extends BaseChartData, PartRender extends IPartRender> implements IChartRender {

    private PartRender mPartRender;
    private IChart mChart;

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected Path mContainerPath;

    private boolean mIsClipContainer = false;
    private boolean mIsAutoZoomYAxis = false;

    private OnCrossPointClickListener mOnCrossPointClickListener;
    private double mYValueRange;

    public BaseChartRenderEx(IChart chart) {
        this.mChart = chart;
        mPartRender = createPartRender(chart);
    }

    private PartRender createPartRender(IChart chart) {
        try {
            Type[] types = ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments();
            Class<?> clazz = (Class<?>) types[1];
            clazz.getConstructor(IChart.class).newInstance(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public float getScale() {
        return mPartRender.getScale();
    }

    @Override
    public void setScale(float scale) {
        mPartRender.setScale(scale);
    }

    @Override
    public float getXDelta() {
        return mPartRender.getXDelta();
    }

    @Override
    public void setXDelta(float xDelta) {
        mPartRender.setXDelta(xDelta);
    }

    @Override
    public boolean isCanZoomLessThanNormal() {
        return mPartRender.isCanZoomLessThanNormal();
    }

    @Override
    public void setCanZoomLessThanNormal(boolean canZoomLessThanNormal) {
        mPartRender.setCanZoomLessThanNormal(canZoomLessThanNormal);
    }

    @Override
    public void setCrossPoint(PointF crossPoint) {
        mPartRender.setCrossPoint(crossPoint);
    }

    @Override
    public PointF getCrossPoint() {
        return mPartRender.getCrossPoint();
    }

    @Override
    public void setCrossColor(int crossColor) {
        mPartRender.setCrossColor(crossColor);
    }

    @Override
    public int getCrossBorderColor() {
        return mPartRender.getCrossBorderColor();
    }

    @Override
    public void setCrossLineWidth(float crossLineWidth) {
        mPartRender.setCrossLineWidth(crossLineWidth);
    }

    @Override
    public void setCrossBorderColor(int color) {
        mPartRender.setCrossBorderColor(color);
    }

    @Override
    public void onMeasure() {
        measureBorder();
        measureAxisPart();
        mPartRender.onMeasure();
    }

    @Override
    public void onDrawFrame(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
        drawAxisScale(canvas);
    }

    @Override
    public void onDrawChart(Canvas canvas) {
        mPartRender.onDrawChart(canvas);
    }

    private void measureBorder() {
        float halfBorder = mChart.getBorder().getWidth() / 2;
        PointF topLeft = new PointF(mChart.getLeft() + halfBorder,
                mChart.getTop() + halfBorder);
        PointF bottomRight = new PointF(mChart.getRight() - halfBorder, mChart.getBottom() - halfBorder);
        mChart.getBorder().setTopLeft(topLeft);
        mChart.getBorder().setBottomRight(bottomRight);
    }

    private void measureAxisPart() {
        measureAxis();
        measureAxisScale();
    }

    private void measureAxis() {
        measureYAxis();
        measureXAxis();
    }

    private void measureYAxis() {
        mTextPaint.setTextSize(mChart.getYAxis().getTextSize());
        mGraphPaint.setStrokeWidth(mChart.getYAxis().getWidth());

        String max = getYAxisTextMaxLength(mChart.getDataList());
        float scaleMaxWidth = mTextPaint.measureText(max);

        float x = 0;

        switch (mChart.getYAxis().getPosition()) {
            case Axis.POSITION_RIGHT:
                if (mChart.getYAxis().isHasScaleText()) {
                    x = mChart.getRight() - (scaleMaxWidth + BaseChart.BLANK * 2);
                } else {
                    x = mChart.getRight();
                }
                mChart.getXAxis().getStartPos().x = 0;
                mChart.getXAxis().getEndPos().x = x;
                break;
            case Axis.POSITION_LEFT:
            default:
                if (mChart.getYAxis().isHasScaleText()) {
                    x = mChart.getLeft() + scaleMaxWidth + BaseChart.BLANK * 2;
                } else {
                    x = mChart.getLeft();
                }
                mChart.getXAxis().getStartPos().x = x;
                mChart.getXAxis().getEndPos().x = mChart.getRight()
                        - mChart.getBorder().getWidth() / 2;
                break;
        }

        mChart.getYAxis().getStartPos().x = x;
        mChart.getYAxis().getEndPos().x = x;
    }

    private String getYAxisTextMaxLength(List<Data> dataList) {
        String maxText = "0.00";
        if (mChart != null) {
            maxText = mChart.getMaxYAxisScaleText();
        }
        if (TextUtils.isEmpty(maxText)) {
            if (dataList == null) {
                return "0.00";
            }

            String max = "0.00";

            return max;
        } else {
            return maxText;
        }
    }

    private void measureXAxis() {
        mTextPaint.setTextSize(mChart.getXAxis().getTextSize());
        mGraphPaint.setStrokeWidth(mChart.getXAxis().getWidth());

        float fontHeight = getFontHeight(mTextPaint);

        float y = 0;

        switch (mChart.getXAxis().getPosition()) {
            case Axis.POSITION_BOTTOM:
                if (mChart.getXAxis().isHasScaleText()) {
                    y = mChart.getBottom() - (fontHeight + BaseChart.BLANK * 2);
                } else {
                    y = mChart.getBottom();
                }
                mChart.getYAxis().getStartPos().y = mChart.getTop() +
                        mChart.getBorder().getWidth() / 2;
                mChart.getYAxis().getEndPos().y = y;
            default:
                break;
            case Axis.POSITION_TOP:
                if (mChart.getXAxis().isHasScaleText()) {
                    y = mChart.getTop() + fontHeight + BaseChart.BLANK * 2;
                } else {
                    y = mChart.getTop();
                }
                mChart.getYAxis().getStartPos().y = y;
                mChart.getYAxis().getEndPos().y = mChart.getBottom() -
                        mChart.getBorder().getWidth() / 2;
                break;
        }

        mChart.getXAxis().getStartPos().y = y;
        mChart.getXAxis().getEndPos().y = y;
    }

    private void measureAxisScale() {
        measureYAxisScale();
        measureXAxisScale();
    }

    private void measureYAxisScale() {
        int grid = mChart.getYAxis().getGrid();
        float yAxisHeight;
        if (mChart.getMarginTextSize() > 0) {
            yAxisHeight = mChart.getYAxis().getEndPos().y -
                    mChart.getYAxis().getStartPos().y
                    - mChart.getMarginTextSize() * 2 - BaseChart.BLANK * 2;
        } else {
            yAxisHeight = mChart.getYAxis().getEndPos().y -
                    mChart.getYAxis().getStartPos().y;
        }

        float blank;
        double scaleValueBlank;
        double minYValue = getYMinValue();
        double maxYValue = getYMaxValue();

        if (grid > 0) {
            blank = yAxisHeight / grid;
            scaleValueBlank = (maxYValue - minYValue) / (grid);
        } else {
            blank = yAxisHeight;
            scaleValueBlank = maxYValue - minYValue;
        }

        mYValueRange = Math.abs(maxYValue - minYValue);

        blank = Math.abs(blank);

        List<Scale> scales = mChart.getYAxis().getScales();
        scales.clear();

        for (int i = 0; i <= grid; i++) {
            Scale scale = new Scale();
            PointF startPt = new PointF();
            PointF endPt = new PointF();
            float y;
            if (mChart.getMarginTextSize() > 0) {
                y = i * blank + mChart.getMarginTextSize() +
                        BaseChart.BLANK + mChart.getYAxis().getStartPos().y;
            } else {
                y = i * blank + mChart.getYAxis().getStartPos().y;
            }
            startPt.x = mChart.getXAxis().getStartPos().x;
            startPt.y = y;
            endPt.x = mChart.getXAxis().getEndPos().x;
            endPt.y = y;

            scale.setStartPos(startPt);
            scale.setEndPos(endPt);

            if (mChart.getMarginTextSize() <= 0) {
                if (i == 0 || i == grid) {
                    scale.setHasLine(false);
                }
            }

            // calculate scale text position
            if (mChart.getYAxis().isHasScaleText()) {
                mTextPaint.setTextSize(mChart.getYAxis().getTextSize());
                float fontHeight = getFontHeight(mTextPaint);

                switch (mChart.getYAxis().getPosition()) {
                    case Axis.POSITION_LEFT:
                    default: {
                        PointF scalePos = new PointF();
                        scalePos.x = mChart.getLeft() + BaseChart.BLANK;
                        scalePos.y = startPt.y;

                        switch (mChart.getYAxis().getTextGravity()) {
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
                        scalePos.x = endPt.x + BaseChart.BLANK;
                        scalePos.y = endPt.y;

                        switch (mChart.getYAxis().getTextGravity()) {
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
                        scale.setScaleText(temp.getConfig().getYValueFormatter().format(
                                maxYValue - i * scaleValueBlank));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mChart.getYAxis().addScale(scale);
        }
    }

    private void measureXAxisScale() {
        float margin = mChart.getXAxis().getScaleMargin();
        int grid = mChart.getXAxis().getGrid();
        float width = mChart.getXAxis().getEndPos().x -
                mChart.getXAxis().getStartPos().x - margin * 2;
        float blank = width / grid;

        List<Scale> scales = mChart.getXAxis().getScales();
        scales.clear();
        for (int i = 0; i <= grid; i++) {
            PointF startPos = new PointF();
            PointF endPos = new PointF();

            startPos.x = mChart.getXAxis().getStartPos().x + i * blank + margin;
            endPos.x = mChart.getXAxis().getStartPos().x + i * blank + margin;

            if (mChart.getMarginTextSize() > 0) {
                startPos.y = mChart.getYAxis().getStartPos().y +
                        mChart.getMarginTextSize() + BaseChart.BLANK;
                endPos.y = mChart.getYAxis().getEndPos().y -
                        (mChart.getMarginTextSize() + BaseChart.BLANK);
            } else {
                startPos.y = mChart.getYAxis().getStartPos().y;
                endPos.y = mChart.getYAxis().getEndPos().y;
            }

            Scale scale = new Scale();
            scale.setStartPos(startPos);
            scale.setEndPos(endPos);

            // add scale text
            if (mChart.getDataList() != null && mChart.getDataList().size() > 0) {
                Object temp1 = mChart.getDataList().get(0);
                if (temp1 instanceof BaseChartData) {
                    BaseChartData dataZero = (BaseChartData) temp1;
                    List showData = dataZero.getShowData();
                    if (showData != null) {
                        int tempIndex;
                        if (showData.size() >= grid) {
                            tempIndex = showData.size() / grid * i;
                            if (tempIndex >= showData.size()) {
                                tempIndex = showData.size() - 1;
                            }
                        } else {
                            tempIndex = (int) (i / (float) grid);
                            if (tempIndex > showData.size() - 1) {
                                tempIndex = showData.size() - 1;
                            }
                        }

                        if (tempIndex < 0) {
                            tempIndex = 0;
                        }

                        Object temp = showData.get(tempIndex);
                        if (temp instanceof BaseEntity) {
                            scale.setScaleText(((BaseEntity) temp).getxValue());
                        }
                    }
                }

            }

            // calculate scale text position
            if (mChart.getXAxis().isHasScaleText()) {
                mTextPaint.setTextSize(mChart.getXAxis().getTextSize());
                float fontHeight = getFontHeight(mTextPaint);
                float textWidth = mTextPaint.measureText(scale.getScaleText());

                switch (mChart.getXAxis().getPosition()) {
                    case Axis.POSITION_TOP:
                    default: {
                        PointF scalePos = new PointF();
                        scalePos.x = startPos.x;
                        scalePos.y = startPos.y - BaseChart.BLANK;

                        switch (mChart.getXAxis().getTextGravity()) {
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
                        scalePos.y = endPos.y + fontHeight + BaseChart.BLANK;

                        if (mChart.getMarginTextSize() > 0) {
                            scalePos.y += mChart.getMarginTextSize()
                                    + BaseChart.BLANK;
                        }

                        switch (mChart.getXAxis().getTextGravity()) {
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

            mChart.getXAxis().addScale(scale);
        }
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
        if (mChart.getYAxis().isHasLine()) {
            mGraphPaint.setStrokeWidth(mChart.getYAxis().getWidth());
            mGraphPaint.setColor(mChart.getYAxis().getColor());
            mGraphPaint.setStyle(Paint.Style.STROKE);

            if (mChart.getYAxis().getLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            canvas.drawLine(mChart.getYAxis().getStartPos().x,
                    mChart.getYAxis().getStartPos().y,
                    mChart.getYAxis().getEndPos().x,
                    mChart.getYAxis().getEndPos().y, mGraphPaint);
        }

        // draw axis x
        if (mChart.getXAxis().isHasLine()) {
            mGraphPaint.setStrokeWidth(mChart.getXAxis().getWidth());
            mGraphPaint.setColor(mChart.getXAxis().getColor());
            mGraphPaint.setStyle(Paint.Style.STROKE);

            if (mChart.getXAxis().getLineType() ==
                    Axis.LINE_TYPE_DASH) {
                PathEffect effects =
                        new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
                mGraphPaint.setPathEffect(effects);
            } else {
                mGraphPaint.setPathEffect(null);
            }

            canvas.drawLine(mChart.getXAxis().getStartPos().x,
                    mChart.getXAxis().getStartPos().y,
                    mChart.getXAxis().getEndPos().x,
                    mChart.getXAxis().getEndPos().y, mGraphPaint);
        }
    }

    private void drawAxisScale(Canvas canvas) {

        mGraphPaint.setStrokeWidth(mChart.getYAxis().getScaleLineWidth());
        mGraphPaint.setColor(mChart.getYAxis().getScaleLineColor());
        mTextPaint.setTextSize(mChart.getYAxis().getTextSize());
        mTextPaint.setColor(mChart.getYAxis().getTextColor());

        if (mChart.getYAxis().getScaleLineType() ==
                Axis.LINE_TYPE_DASH) {
            PathEffect effects =
                    new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
            mGraphPaint.setPathEffect(effects);
        } else {
            mGraphPaint.setPathEffect(null);
        }

        boolean hasScaleLine = mChart.getYAxis().isHasScaleLine();
        boolean hasScaleText = mChart.getYAxis().isHasScaleText();

        for (Scale scale : mChart.getYAxis().getScales()) {
            if (hasScaleLine) {
                Path path = new Path();
                path.moveTo(scale.getStartPos().x, scale.getStartPos().y);
                path.lineTo(scale.getEndPos().x, scale.getEndPos().y);
                canvas.drawPath(path, mGraphPaint);
            }

            if (hasScaleText) {
                canvas.drawText(scale.getScaleText(), scale.getScaleTextPos().x,
                        scale.getScaleTextPos().y, mTextPaint);
            }
        }

        mGraphPaint.setStrokeWidth(mChart.getXAxis().getScaleLineWidth());
        mGraphPaint.setColor(mChart.getXAxis().getScaleLineColor());

        if (mChart.getXAxis().getScaleLineType() ==
                Axis.LINE_TYPE_DASH) {
            PathEffect effects =
                    new DashPathEffect(new float[]{10, 10, 10, 10}, 1);
            mGraphPaint.setPathEffect(effects);
        } else {
            mGraphPaint.setPathEffect(null);
        }

        hasScaleLine = mChart.getXAxis().isHasScaleLine();
        hasScaleText = mChart.getXAxis().isHasScaleText();

        for (Scale scale : mChart.getXAxis().getScales()) {
            if (hasScaleLine) {
                Path path = new Path();
                path.moveTo(scale.getStartPos().x, scale.getStartPos().y);
                path.lineTo(scale.getEndPos().x, scale.getEndPos().y);
                canvas.drawPath(path, mGraphPaint);
            }

            if (hasScaleText) {
                canvas.drawText(scale.getScaleText(), scale.getScaleTextPos().x,
                        scale.getScaleTextPos().y, mTextPaint);
            }
        }
    }

    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (fm.descent - fm.ascent) / 3 * 2;
    }

    @Override
    public double getYMaxValue() {
        return mChart.getYMaxValue();
    }

    @Override
    public double getYMinValue() {
        return mChart.getYMinValue();
    }

    @Override
    public void setIsClipContainer(boolean isClipContainer) {
        this.mIsClipContainer = isClipContainer;
    }

    @Override
    public void setOnCrossPointClickListener(OnCrossPointClickListener onCrossPointClickListener) {
        this.mOnCrossPointClickListener = onCrossPointClickListener;
    }

    @Override
    public boolean isClipContainer() {
        return mIsClipContainer;
    }

    @Override
    public boolean isAutoZoomYAxis() {
        return mIsAutoZoomYAxis;
    }

    @Override
    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {
        this.mIsAutoZoomYAxis = isAutoZoomYAxis;
    }

}
