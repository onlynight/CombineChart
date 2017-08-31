package com.github.onlynight.chartlibrary.render;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Region;
import android.text.TextUtils;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Scale;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.operate.IChartInterface;
import com.github.onlynight.chartlibrary.render.part.BasePartRender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/10.
 */

public abstract class BaseChartRender<T extends BaseChartData, PartRender extends BasePartRender> implements
        IChartInterface {

    protected Paint mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected BaseChart mChart;
    protected Path mContainerPath;

    private boolean mIsClipContainer = false;
    private boolean mIsAutoZoomYAxis = false;

    private OnResetExtremeValueListener onResetExtremeValueListener;

    private PartRender mPartRender;
    private double mYValueRange = 0;

    public BaseChartRender(BaseChart chart) {
        this.mChart = chart;
        this.mContainerPath = new Path();
        mPartRender = createPartRender(chart);
    }

    public abstract PartRender createPartRender(BaseChart chart);

    public void setOnResetExtremeValueListener(OnResetExtremeValueListener onResetExtremeValueListener) {
        this.onResetExtremeValueListener = onResetExtremeValueListener;
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
    }

    private void measureAxisPart() {
        measureAxis();
        measureAxisScale();
    }

    public void onDraw(Canvas canvas) {
        drawBorder(canvas);
        drawAxis(canvas);
        drawAxisScale(canvas);
        drawCrossBorder(canvas);
    }

    private void drawCrossBorder(Canvas canvas) {
        int border = 5;
        PointF point = mPartRender.getCrossPoint();
        if (point != null && point.x > 0 && point.y > 0) {
            if (mPartRender.isInChartRange(point)) {
                mGraphPaint.setColor(mPartRender.getCrossBorderColor());
                mGraphPaint.setStyle(Paint.Style.FILL);

                float textSize = mChart.getyAxis().getTextSize();

                if (mPartRender.isInChartYRange(point)) {
                    mTextPaint.setTextSize(textSize);
                    mTextPaint.setColor(Color.WHITE);
                    float fontHeight = getFontHeight(mTextPaint);
                    float height = fontHeight + border * 2;
                    canvas.drawRect(mChart.getxAxis().getEndPos().x, point.y - height / 2,
                            mChart.getRight(), point.y + height / 2, mGraphPaint);

                    float value = Math.abs(point.y - mChart.getyAxis().getEndPos().y);
                    float yAxisHeight = Math.abs(mChart.getyAxis().getEndPos().y -
                            mChart.getyAxis().getStartPos().y);

                    try {
                        double text = value / yAxisHeight * mYValueRange + getYMinValue();
                        String temp = ((BaseChartData) mChart.getDataList().get(0)).getConfig().getYValueFormatter().format(text);
                        canvas.drawText(temp, mChart.getxAxis().getEndPos().x + BaseChart.BLANK,
                                point.y - height / 2 + border + fontHeight, mTextPaint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (mChart.getxAxis().isHasLine()) {
                    List<BaseEntity> entities = getSelectEntities();
                    if (entities != null && entities.size() > 0) {
                        textSize = mChart.getxAxis().getTextSize();
                        mTextPaint.setTextSize(textSize);
                        mTextPaint.setColor(Color.WHITE);
                        long time = entities.get(0).getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String timeStr = sdf.format(time);
                        float width = mTextPaint.measureText(timeStr) + border * 2;
                        float start = point.x - width / 2;
                        float top = mChart.getxAxis().getStartPos().y;
                        canvas.drawRect(start, top,
                                point.x + width / 2, mChart.getBottom(), mGraphPaint);

                        canvas.drawText(timeStr, start + border,
                                top + getFontHeight(mTextPaint) + border, mTextPaint);
                    }
                }
            }
        }
    }

    public void onDrawChart(Canvas canvas) {
        if (mIsClipContainer) {
            clipContainer(canvas);
        }

        mPartRender.onDrawChart(canvas);
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
        String maxText = "0.00";
        if (mChart != null) {
            maxText = mChart.generateMaxLengthYAxisScaleText();
        }
        if (TextUtils.isEmpty(maxText)) {
            if (dataList == null) {
                return "0.00";
            }

            String max = "0.00";
            for (T data : dataList) {
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

    private String getYAxisTextMaxLength(T chartData) {
        if (chartData == null ||
                chartData.getData() == null) {
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
            scaleValueBlank = (maxYValue - minYValue) / (grid);
        } else {
            blank = yAxisHeight;
            scaleValueBlank = maxYValue - minYValue;
        }

        mYValueRange = Math.abs(maxYValue - minYValue);

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
                        scale.setScaleText(temp.getConfig().getYValueFormatter().format(
                                maxYValue - i * scaleValueBlank));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mChart.getyAxis().addScale(scale);
        }
    }

    private void measureXAxisScale() {
        float margin = mChart.getxAxis().getScaleMargin();
        int grid = mChart.getxAxis().getGrid();
        float width = mChart.getxAxis().getEndPos().x -
                mChart.getxAxis().getStartPos().x - margin * 2;
        float blank = width / grid;

        List<Scale> scales = mChart.getxAxis().getScales();
        scales.clear();
        for (int i = 0; i <= grid; i++) {
            PointF startPos = new PointF();
            PointF endPos = new PointF();

            startPos.x = mChart.getxAxis().getStartPos().x + i * blank + margin;
            endPos.x = mChart.getxAxis().getStartPos().x + i * blank + margin;

            if (mChart.getMarginTextSize() > 0) {
                startPos.y = mChart.getyAxis().getStartPos().y +
                        mChart.getMarginTextSize() + BaseChart.BLANK;
                endPos.y = mChart.getyAxis().getEndPos().y -
                        (mChart.getMarginTextSize() + BaseChart.BLANK);
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
                                    + BaseChart.getBLANK();
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

        boolean hasScaleLine = mChart.getyAxis().isHasScaleLine();
        boolean hasScaleText = mChart.getyAxis().isHasScaleText();

        for (Scale scale : mChart.getyAxis().getScales()) {
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

        hasScaleLine = mChart.getxAxis().isHasScaleLine();
        hasScaleText = mChart.getxAxis().isHasScaleText();

        for (Scale scale : mChart.getxAxis().getScales()) {
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

    protected void clipContainer(Canvas canvas) {
        List<Scale> scales = mChart.getyAxis().getScales();
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
            if (onResetExtremeValueListener != null) {
                onResetExtremeValueListener.onResetExtremeValue();
            }
            measureAxisPart();
        }

        mPartRender.setScale(scale);
    }

    @Override
    public float getScale() {
        return mPartRender.getScale();
    }

    @Override
    public void setxDelta(float xDelta) {
        if (mIsAutoZoomYAxis) {
            cutBarEntities();
            if (onResetExtremeValueListener != null) {
                onResetExtremeValueListener.onResetExtremeValue();
            }
            measureAxisPart();
        }

        mPartRender.setxDelta(xDelta);
    }

    @Override
    public float getxDelta() {
        return mPartRender.getxDelta();
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
                        if (temp1.getX() >= mChart.getxAxis().getStartPos().x) {
                            startIndex = i;
                            break;
                        }
                    }

                    for (int i = 0; i < data.getData().size(); i++) {
                        temp1 = (BaseEntity) data.getData().get(i);
                        if (temp1.getX() >= mChart.getxAxis().getEndPos().x) {
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

    public List<BaseEntity> getSelectEntities() {
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

    public interface OnResetExtremeValueListener {

        void onResetExtremeValue();

    }

}
