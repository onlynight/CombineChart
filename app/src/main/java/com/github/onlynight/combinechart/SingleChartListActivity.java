package com.github.onlynight.combinechart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.onlynight.chartlibrary.chart.impl.CombineChart;
import com.github.onlynight.chartlibrary.chart.part.Axis;
import com.github.onlynight.chartlibrary.chart.part.Border;
import com.github.onlynight.chartlibrary.data.CandleStickChartData;
import com.github.onlynight.chartlibrary.data.LineChartData;
import com.github.onlynight.chartlibrary.data.config.CandleStickChartDataConfig;
import com.github.onlynight.chartlibrary.data.config.LineChartDataConfig;
import com.github.onlynight.chartlibrary.data.entity.CandleStickEntity;
import com.github.onlynight.chartlibrary.data.entity.LineEntity;
import com.github.onlynight.combinechart.formatter.TimeValueFormatter;
import com.github.onlynight.combinechart.formatter.YValueFormatter;
import com.github.onlynight.combinechart.repository.DataRepository;

import java.util.List;

import static com.github.onlynight.chartlibrary.util.Utils.initMA;

public class SingleChartListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SingleChartsRecyclerAdapter mAdapter;

    private DataRepository mDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chart_list);

        mDataRepository = new DataRepository();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SingleChartsRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < 10; i++) {
            mAdapter.addData(generateCombineChart1());
        }

        mAdapter.notifyDataSetChanged();
    }

    private CombineChart generateCombineChart1() {
        CombineChart candleChart = new CombineChart();
        candleChart.setMarginTextSize(getResources().getDimension(R.dimen.textSize10));
//        CandleStickChartData data = generateCandleChartData();

        CandleStickChartDataConfig config = new CandleStickChartDataConfig();
        config.setAutoWidth(true);
        config.setIncreasingColor(Color.RED);
        config.setDecreasingColor(Color.GREEN);
        config.setStrokeWidth(2f);
        YValueFormatter formatter = new YValueFormatter();
        formatter.setFormat("0.00000000");
        config.setYValueFormatter(formatter);
        config.setXValueFormatter(new TimeValueFormatter());

        CandleStickChartData data = new CandleStickChartData(config);
        List<CandleStickEntity> entities = mDataRepository.createLocalCandleStickData(90);
        data.setData(entities);
        candleChart.addData(data);
        LineChartData data5 = generateCombineLineData1(Color.GRAY, 5, data);
        candleChart.addData(data5);
        LineChartData data10 = generateCombineLineData1(Color.CYAN, 10, data);
        candleChart.addData(data10);
        LineChartData data20 = generateCombineLineData1(Color.MAGENTA, 20, data);
        candleChart.addData(data20);

        Axis y = candleChart.getYAxis();
        y.setPosition(Axis.POSITION_RIGHT);
        y.setColor(Color.BLACK);
        y.setWidth(1);
        y.setLineType(Axis.LINE_TYPE_DASH);
        y.setTextSize(getResources().getDimension(R.dimen.textSize10));
        y.setGrid(5);
        y.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        y.setTextColor(Color.BLACK);

        Axis x = candleChart.getXAxis();
        x.setPosition(Axis.POSITION_BOTTOM);
        x.setColor(Color.BLACK);
        x.setWidth(1);
        x.setTextSize(getResources().getDimension(R.dimen.textSize10));
        x.setHasLine(true);
        x.setHasScaleText(true);
        x.setHasScaleLine(true);
        x.setTextGravity(Axis.TEXT_GRAVITY_CENTER);
        x.setTextColor(Color.BLACK);

        Border border = candleChart.getBorder();
        border.setWidth(1);
        border.setColor(Color.GRAY);

        return candleChart;
    }

    private CandleStickChartData generateCandleChartData() {
        CandleStickChartDataConfig config = new CandleStickChartDataConfig();
        config.setYValueFormatter(new YValueFormatter());
        config.setBarWidth(10);
        config.setIncreasingColor(Color.RED);
        config.setDecreasingColor(Color.GREEN);
        config.setStrokeWidth(2f);
        config.setAutoWidth(true);

        CandleStickChartData data = new CandleStickChartData(config);
        List<CandleStickEntity> entities = mDataRepository.generateCandleStickEntities();
        data.setData(entities);

        return data;
    }

    private LineChartData generateCombineLineData1(
            int lineColor, int days, CandleStickChartData candleStickChartData) {

        LineChartDataConfig config = new LineChartDataConfig();
        config.setYValueFormatter(new YValueFormatter());
        config.setColor(lineColor);
        config.setStrokeWidth(2f);
        config.setAutoWidth(true);

        LineChartData data = new LineChartData(config);

        List<LineEntity> entities = initMA(days, candleStickChartData);
        data.setData(entities);

        return data;
    }

}
