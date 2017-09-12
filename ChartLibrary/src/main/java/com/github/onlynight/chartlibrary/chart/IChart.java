package com.github.onlynight.chartlibrary.chart;

import android.graphics.Canvas;

import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.BaseChartData;

import java.util.List;

/**
 * Created by lion on 2017/8/21.
 */

public interface IChart<T extends BaseChartData> extends IChartOperate {

    void setWeight(double weight);

    double getWeight();

    void setArea(int left, int top, int right, int bottom);

    void onMeasure();

    void onDrawFrame(Canvas canvas);

    void onDrawChart(Canvas canvas);

    List<T> getDataList();

    void clearData();

    int getWidth();

    int getHeight();

    Axis getXAxis();

    Axis getYAxis();

    void setIsAutoZoomYAxis(boolean isAutoZoomYAxis);

    boolean isAutoZoomYAxis();

    float getMarginTextSize();

    void setMarginTextSize(float marginTextSize);

    String getMaxYAxisScaleText();

    void setMaxYAxisScaleText(String maxYAxisScaleText);

    void setIsClipContainer(boolean clipContainer);

    boolean isClipContainer();

    boolean isShowLegend();

    void setIsShowLegend(boolean isShowLegend);

    int getMarginTextColor();

    void setMarginTextColor(int marginTextColor);

    int getLeft();

    void setLeft(int mLeft);

    int getTop();

    void setTop(int mTop);

    int getRight();

    void setRight(int mRight);

    int getBottom();

    void setBottom(int mBottom);

    Border getBorder();

    void setBorder(Border border);

    void setXAxis(Axis xAxis);

    void setYAxis(Axis yAxis);

    void addData(T data);

    double getYMaxValue();

    double getYMinValue();
}
