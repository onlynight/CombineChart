package com.github.onlynight.combinechart.formatter;

import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.data.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by lion on 2017/8/28.
 */

public class YValueFormatter extends ValueFormatter {

    private DecimalFormat df;

    public YValueFormatter() {
        this.df = new DecimalFormat("0.00");
    }

    public void setFormat(String format) {
        df = new DecimalFormat(format);
    }

    @Override
    public String format(BaseEntity entity) {
        return df.format(entity.getY());
    }

    @Override
    public String format(Object object) {
        if (object instanceof Float ||
                object instanceof Double) {
            return df.format(object);
        }
        return null;
    }
}
