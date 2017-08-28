package com.github.onlynight.chartlibrary.data.formatter;

import com.github.onlynight.chartlibrary.data.entity.BaseEntity;

/**
 * Created by lion on 2017/8/24.
 */

public abstract class ValueFormatter<T extends BaseEntity> {

    public abstract String format(T entity);

    public abstract String format(Object object);

}
