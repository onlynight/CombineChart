package com.github.onlynight.combinechart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.onlynight.chartlibrary.chart.BaseChart;
import com.github.onlynight.chartlibrary.chart.CombineChart;
import com.github.onlynight.chartlibrary.data.BaseChartData;
import com.github.onlynight.chartlibrary.data.entity.BaseEntity;
import com.github.onlynight.chartlibrary.util.Utils;
import com.github.onlynight.chartlibrary.view.CombineChartView;
import com.github.onlynight.combinechart.chart.KLineChartFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseChart.OnCrossPointClickListener {

    private TextView mTextCrossPoint1;
    private TextView mTextCrossPoint2;
    private CombineChartView mCombineChartView;
    private KLineChartFactory mKLineChartFactory;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKLineChartFactory = new KLineChartFactory(this);

        mTextCrossPoint1 = (TextView) findViewById(R.id.textCrossPoint1);
        mTextCrossPoint2 = (TextView) findViewById(R.id.textCrossPoint2);
        mCombineChartView = (CombineChartView) findViewById(R.id.chart);
        mCombineChartView.setOperatable(true);
        mCombineChartView.setIsShowCrossPoint(true);
        CombineChart candleChart = generateCombineChart1();
        candleChart.setOnCrossPointClickListener(this);
        mCombineChartView.addChart(candleChart);
        mCombineChartView.addChart(generateCombineChart2());
        mCombineChartView.addChart(generateCombineChart3());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCombineChartView.setxDelta(mCombineChartView.getxDelta());
                mCombineChartView.invalidate();
            }
        }, 200);
    }

    private CombineChart generateCombineChart1() {
        return mKLineChartFactory.generateKLineChart();
    }

    private CombineChart generateCombineChart2() {
        return mKLineChartFactory.generateVolChart();
    }

    private CombineChart generateCombineChart3() {
        return mKLineChartFactory.generateVolChart1();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuSingleChart:
                Intent intent = new Intent(this, SingleChartListActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCrossPointClick(List<BaseEntity> entities, List<BaseChartData> chartsData) {
        if (entities == null || chartsData == null) {
            mTextCrossPoint1.setText("");
            mTextCrossPoint2.setText("");
        } else {
            SpannableStringBuilder sb = Utils.getChartCrossPointSpannable(
                    this, entities, chartsData, 8f);
            mTextCrossPoint1.setText(Utils.getChartCrossPointCandleStick(entities, chartsData));
            mTextCrossPoint2.setText(sb);
        }
    }
}
