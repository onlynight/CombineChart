package com.github.onlynight.combinechart.formatter;

import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.data.formatter.ValueFormatter;

import java.text.SimpleDateFormat;

/**
 * Created by lion on 2017/8/30.
 */

public class TimeValueFormatter extends ValueFormatter {

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Override
    public String format(BaseEntity entity) {
        return sdf.format(entity.getTime());
    }

    @Override
    public String format(Object object) {
        if (object instanceof Long) {
            return sdf.format(object);
        }
        return null;
    }
}
