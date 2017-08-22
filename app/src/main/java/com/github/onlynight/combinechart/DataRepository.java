package com.github.onlynight.combinechart;

import android.graphics.Color;

import com.github.onlynight.chartlibrary.data.entity.BarEntity;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lion on 2017/8/21.
 */

public class DataRepository {

    public List<CandleStickEntity> generateCandleStickEntities() {
        List<CandleStickEntity> entities = new ArrayList<>();

        CandleStickEntity entity;
        for (int i = 0; i < 5; i++) {
            entity = new CandleStickEntity();
            entity.setOpen(1);
            entity.setClose(10);
            entity.setLow(0.5);
            entity.setHigh(12);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(10);
            entity.setClose(5);
            entity.setLow(5);
            entity.setHigh(10);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(5);
            entity.setClose(15);
            entity.setLow(2);
            entity.setHigh(18);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(15);
            entity.setClose(10);
            entity.setLow(5);
            entity.setHigh(20);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(10);
            entity.setClose(20);
            entity.setLow(3);
            entity.setHigh(20);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(20);
            entity.setClose(5);
            entity.setLow(1);
            entity.setHigh(20);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(5);
            entity.setClose(16);
            entity.setLow(5);
            entity.setHigh(20);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(15);
            entity.setClose(28);
            entity.setLow(0);
            entity.setHigh(30);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(16);
            entity.setClose(10);
            entity.setLow(5);
            entity.setHigh(17);
            entities.add(entity);

            entity = new CandleStickEntity();
            entity.setOpen(10);
            entity.setClose(15);
            entity.setLow(5);
            entity.setHigh(17);
            entities.add(entity);
        }

        return entities;
    }

    public List<LineEntity> generateCombineLineEntities() {
        List<LineEntity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            LineEntity entity = new LineEntity();
            entity.setX(i);
            entity.setY(Math.abs(random.nextInt() * 100 % 30));
            entities.add(entity);
        }

        return entities;
    }

    public List<BarEntity> generateBarEntities() {
        List<BarEntity> entities = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            BarEntity entity = new BarEntity();
            entity.setX(i);
            entity.setY(Math.abs(random.nextInt() * 100 % 30));
            entity.setColor(i % 2 == 0 ? Color.RED : Color.GREEN);
            entities.add(entity);
        }
        return entities;
    }

}
