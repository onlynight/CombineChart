package com.github.onlynight.chartlibrary.render.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Region;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.chart.OnCrossPointClickListener;
import com.github.onlynight.chartlibrary.chart.impl.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.render.IChartRender;
import com.github.onlynight.chartlibrary.render.part.IPartRender;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public class BaseChartRender<Data extends BaseChartData, PartRender extends IPartRender>
        implements IChartRender {

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected BaseChart mChart;
    protected Path mContainerPath;

    private boolean mIsClipContainer = false;
    private boolean mIsAutoZoomYAxis = false;

    private PartRender mPartRender;
    private double mYValueRange = 0;
    private OnCrossPointClickListener mOnCrossPointClickListener;

    public BaseChartRender(BaseChart chart) {
        this.mChart = chart;
        this.mContainerPath = new Path();
        mPartRender = createPartRender(chart);
    }

    private PartRender createPartRender(BaseChart chart) {
        try {
            Class<? extends BaseChartRender> thisClass = getClass();
            Type type = thisClass.getGenericSuperclass();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> clazz = (Class<?>) types[1];
            return (PartRender) clazz.getConstructor(BaseChart.class).newInstance(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setOnCrossPointClickListener(OnCrossPointClickListener onCrossPointClickListener) {
        this.mOnCrossPointClickListener = onCrossPointClickListener;
    }

    public boolean isClipContainer() {
        return mIsClipContainer;
    }

    public void setIsClipContainer(boolean isClipContainer) {
        this.mIsClipContainer = isClipContainer;
    }

    public void onMeasure() {
        measureBorder();
        measureAxisPart();
        mPartRender.onMeasure();
    }

    private void measureAxisPart() {
        measureAxis();
        measureAxisScale();
    }

    public void onDrawFrame(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
        drawAxisScale(canvas);
        drawCrossBorder(canvas);
    }

    private void drawCrossBorder(Canvas canvas) {
        int border = 5;
        PointF point = mPartRender.getCrossPoint();
        if (point != null && point.x > 0 && point.y > 0) {
            if (isInChartRange(point)) {
                mGraphPaint.setColor(mPartRender.getCrossBorderColor());
                mGraphPaint.setStyle(Paint.Style.FILL);

                float textSize = mChart.getYAxis().getTextSize();

                if (isInChartYRange(point)) {
                    mTextPaint.setTextSize(textSize);
                    mTextPaint.setColor(Color.WHITE);
                    float fontHeight = getFontHeight(mTextPaint);
                    float height = fontHeight + border * 2;
                    canvas.drawRect(mChart.getXAxis().getEndPos().x, point.y - height / 2,
                            mChart.getRight(), point.y + height / 2, mGraphPaint);

                    float value = Math.abs(point.y - mChart.getYAxis().getEndPos().y);
                    float yAxisHeight = Math.abs(mChart.getYAxis().getEndPos().y -
                            mChart.getYAxis().getStartPos().y);

                    try {
                        double text = value / yAxisHeight * mYValueRange + getYMinValue();
                        String temp = ((BaseChartData) mChart.getDataList().get(0)).getConfig().getYValueFormatter().format(text);
                        canvas.drawText(temp, mChart.getXAxis().getEndPos().x + BaseChart.BLANK,
                                point.y - height / 2 + border + fontHeight, mTextPaint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                List<BaseEntity> entities = getSelectEntities();

                if (entities != null) {
                    drawLegend(canvas, entities);

                    if (mChart.getXAxis().isHasLine()) {
                        if (entities.size() > 0) {
                            textSize = mChart.getXAxis().getTextSize();
                            mTextPaint.setTextSize(textSize);
                            mTextPaint.setColor(Color.WHITE);
                            long time = entities.get(0).getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String timeStr = sdf.format(time);
                            float width = mTextPaint.measureText(timeStr) + border * 2;
                            float start = point.x - width / 2;
                            float top = mChart.getXAxis().getStartPos().y;
                            canvas.drawRect(start, top,
                                    point.x + width / 2, mChart.getBottom(), mGraphPaint);

                            canvas.drawText(timeStr, start + border,
                                    top + getFontHeight(mTextPaint) + border, mTextPaint);
                        }
                    }

                    if (mOnCrossPointClickListener != null) {
                        mOnCrossPointClickListener.onCrossPointClick(entities, mChart.getDataList());
                    }
                }
            } else {
                if (mOnCrossPointClickListener != null) {
                    mOnCrossPointClickListener.onCrossPointClick(null, null);
                }
            }
        } else {
            if (mOnCrossPointClickListener != null) {
                mOnCrossPointClickListener.onCrossPointClick(null, null);
            }
        }
    }

    private void drawLegend(Canvas canvas, List<BaseEntity> legendEntities) {
        if (mChart == null || mChart.getDataList() == null) {
            return;
        }

        if (legendEntities == null) {
            return;
        }

        if (!mChart.isShowLegend()) {
            return;
        }

        int chartSize = mChart.getDataList().size();
        int legendEntitiesSize = legendEntities.size();

        if (chartSize != legendEntitiesSize) {
            return;
        }

        float left = 0;

        for (int i = 0; i < chartSize; i++) {
            BaseChartData chartData = (BaseChartData) mChart.getDataList().get(i);
            if (!(chartData instanceof CandleStickChartData)) {
                BaseChartDataConfig config = chartData.getConfig();
                int color = config.getColor();
                mTextPaint.setColor(color);
                mTextPaint.setTextSize(mChart.getMarginTextSize());
                float fontHeight = getFontHeight(mTextPaint);

                BaseEntity entity = legendEntities.get(i);
                String text = chartData.getChartName() + ":" + entity.getyValue();
                try {
                    text = chartData.getChartName() + ":" +
                            chartData.getConfig().getYValueFormatter().format(entity.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                canvas.drawText(text, mChart.getLeft() + BaseChart.BLANK + left,
                        mChart.getTop() + fontHeight + BaseChart.BLANK, mTextPaint);

                float width = mTextPaint.measureText(text);
                left += width + BaseChart.BLANK * 3;
            }
        }
    }

    public void onDrawChart(Canvas canvas) {
        if (mIsClipContainer) {
            clipContainer(canvas);
        }

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
            for (Data data : dataList) {
                String temp = getYAxisTextMaxLength(data);
                if (temp.length() > max.length()) {
                    max = temp;
                }
            }

            return max;
        } else {
            return maxText;
        }
    }

    private String getYAxisTextMaxLength(Data data) {

        if (data == null || data.getData() == null) {
            return "";
        }

        String max = "";
        for (Object temp : data.getData()) {
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

    @Override
    public double getYMaxValue() {
        return mChart.getYMaxValue();
    }

    @Override
    public double getYMinValue() {
        return mChart.getYMinValue();
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

    private void clipContainer(Canvas canvas) {
        List<Scale> scales = mChart.getYAxis().getScales();
        if (scales != null && scales.size() > 0) {
            mContainerPath.reset();
//            canvas.clipPath(mContainerPath);
//            mContainerPath.addCircle(mChart.getWidth() / 2, mChart.getHeight() / 2,
//                    mChart.getWidth() / 2, Path.Direction.CCW);
            float startX = scales.get(0).getStartPos().x;
//            float startY = scales.get(0).getStartPos().y;
            float startY = mChart.getTop();
            float endX = scales.get(scales.size() - 1).getEndPos().x;
//            float endY = scales.get(scales.size() - 1).getEndPos().y;
            float endY = mChart.getBottom();
            mContainerPath.addRect(startX, startY, endX, endY, Path.Direction.CCW);
            canvas.clipPath(mContainerPath, Region.Op.REPLACE);//Region.Op.REPLACE
        }
    }

    @Override
    public void setScale(float scale) {
        if (mIsAutoZoomYAxis) {
            cutBarEntities();
            measureAxisPart();
        }

        mPartRender.setScale(scale);
    }

    @Override
    public float getScale() {
        return mPartRender.getScale();
    }

    @Override
    public void setXDelta(float xDelta) {
        if (mIsAutoZoomYAxis) {
            cutBarEntities();
            measureAxisPart();
        }

        mPartRender.setXDelta(xDelta);
    }

    @Override
    public float getXDelta() {
        return mPartRender.getXDelta();
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

    public boolean isAutoZoomYAxis() {
        return mIsAutoZoomYAxis;
    }

    public void setIsAutoZoomYAxis(boolean isAutoZoomYAxis) {
        this.mIsAutoZoomYAxis = isAutoZoomYAxis;
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

    private void cutBarEntities() {
        if (mChart != null) {
            for (Object temp : mChart.getDataList()) {
                if (temp instanceof BaseChartData) {
                    BaseChartData data = (BaseChartData) temp;
                    int startIndex = 0;
                    int endIndex = data.getData().size() - 1;

                    BaseEntity temp1 = null;
                    for (int i = 0; i < data.getData().size(); i++) {
                        temp1 = (BaseEntity) data.getData().get(i);
                        if (temp1.getX() >= mChart.getXAxis().getStartPos().x) {
                            startIndex = i;
                            break;
                        }
                    }

                    for (int i = 0; i < data.getData().size(); i++) {
                        temp1 = (BaseEntity) data.getData().get(i);
                        if (temp1.getX() >= mChart.getXAxis().getEndPos().x) {
                            endIndex = i;
                            break;
                        }
                    }

                    if (startIndex < endIndex) {
                        List subList =
                                data.getData().subList(startIndex, endIndex);
                        data.setShowData(subList);
                    }

                }
            }
        }
    }

    private int getSelectDataEntityIndex(PointF point) {
        if (point == null) {
            return -1;
        }

        if (mChart.getDataList() != null && mChart.getDataList().size() > 0) {
            BaseChartData chartData = (BaseChartData) mChart.getDataList().get(0);
            if (chartData != null && chartData.getShowData() != null) {
                for (int i = 0; i < chartData.getShowData().size(); i++) {
                    BaseEntity entity = (BaseEntity) chartData.getShowData().get(i);
                    if (entity != null) {
                        if (entity.getX() > point.x) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private List<BaseEntity> getSelectEntities() {
        if (mChart.getDataList() == null) {
            return null;
        }

        int index = getSelectDataEntityIndex(mPartRender.getCrossPoint());
        if (index == -1) {
            return null;
        }

        List<BaseEntity> entities = new ArrayList<>();
        for (int i = 0; i < mChart.getDataList().size(); i++) {
            BaseChartData chartData = (BaseChartData) mChart.getDataList().get(i);
            BaseEntity entity = null;
            try {
                entity = (BaseEntity) chartData.getShowData().get(index);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entity != null) {
                entities.add(entity);
            }
        }

        return entities;
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
