package com.github.onlynight.chartlibrary.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.config.BaseChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/23.
 */

public class Utils {

    public static List<LineEntity> initMA(int days, CandleStickChartData data) {

        if (days < 2 || data == null || data.getData() == null) {
            return null;
        }

        List<LineEntity> MA_X_Values = new ArrayList<>();

        float sum = 0;
        float avg = 0;
        List<CandleStickEntity> tempData = data.getData();
        for (int i = 0; i < tempData.size(); i++) {
            float close = (float) tempData.get(i).getClose();
            if (i < days) {
                sum = sum + close;
                avg = sum / (i + 1f);
            } else {
                sum = sum + close
                        - (float) tempData.get(i - days).getClose();
                avg = sum / days;
            }

            LineEntity entity = new LineEntity();
            entity.setX(i);
            entity.setY(avg);
            MA_X_Values.add(entity);
        }

        return MA_X_Values;
    }

    public static List<LineEntity> initMA(int days, BarChartData data) {

        if (days < 2 || data == null || data.getData() == null) {
            return null;
        }

        List<LineEntity> MA_X_Values = new ArrayList<>();

        float sum = 0;
        float avg = 0;
        List<BarEntity> tempData = data.getData();
        for (int i = 0; i < tempData.size(); i++) {
            float close = (float) tempData.get(i).getY();
            if (i < days) {
                sum = sum + close;
                avg = sum / (i + 1f);
            } else {
                sum = sum + close
                        - (float) tempData.get(i - days).getY();
                avg = sum / days;
            }

            LineEntity entity = new LineEntity();
            entity.setX(i);
            entity.setY(avg);
            MA_X_Values.add(entity);
        }

        return MA_X_Values;
    }

    public static SpannableStringBuilder getChartCrossPointSpannable(Context context,
                                                                     List<BaseEntity> legendEntities,
                                                                     List<BaseChartData> chartsData,
                                                                     float textSize) {

        if (legendEntities == null || chartsData == null) {
            return null;
        }

        int chartSize = chartsData.size();
        int legendEntitiesSize = legendEntities.size();

        if (chartSize != legendEntitiesSize) {
            return null;
        }

        String content = "";
        int[] indexes = new int[chartSize];
        int[] colors = new int[chartSize];

        for (int i = 0; i < chartSize; i++) {
            BaseChartData chartData = chartsData.get(i);

            if (chartData instanceof CandleStickChartData) {
            } else {
                BaseChartDataConfig config = chartData.getConfig();
                int color = config.getColor();

                BaseEntity entity = legendEntities.get(i);
                String text = chartData.getChartName() + ":" + entity.getyValue();
                try {
                    text = chartData.getChartName() + ":" +
                            chartData.getConfig().getYValueFormatter().format(entity.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String temp = text + "  ";
                content += temp;
                indexes[i] = content.length();
                colors[i] = color;
            }
        }

        SpannableStringBuilder sb = new SpannableStringBuilder(content); // 包装字体内容

        AbsoluteSizeSpan decass = new AbsoluteSizeSpan(DisplayUtil.sp2px(context, textSize));  // 设置字体大小
        sb.setSpan(decass, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int i = 0; i < chartSize; i++) {
            ForegroundColorSpan fcs = new ForegroundColorSpan(colors[i]);
            if (i == 0) {
                sb.setSpan(fcs, 0, indexes[i],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                sb.setSpan(fcs, indexes[i - 1], indexes[i],
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        return sb;
    }

    public static String getChartCrossPointCandleStick(List<BaseEntity> legendEntities,
                                                       List<BaseChartData> chartsData) {
        if (legendEntities == null || chartsData == null) {
            return null;
        }

        int chartSize = chartsData.size();
        int legendEntitiesSize = legendEntities.size();

        if (chartSize != legendEntitiesSize) {
            return null;
        }

        String content = "";

        for (int i = 0; i < chartSize; i++) {
            BaseChartData chartData = chartsData.get(i);
            if (chartData instanceof CandleStickChartData) {
                CandleStickEntity data = (CandleStickEntity) legendEntities.get(i);
                content = "open:" + data.getOpen() + "  "
                        + "high:" + data.getHigh() + "  "
                        + "low:" + data.getLow() + "  " +
                        "close:" + data.getClose();
            }
        }

        return content;
    }
}