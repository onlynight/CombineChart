package com.github.onlynight.chartlibrary.util;

import com.github.onlynight.chartlibrary.data.BarChartData;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.entity.BarEntity;
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

}
