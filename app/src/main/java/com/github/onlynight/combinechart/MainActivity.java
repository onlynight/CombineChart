package com.github.onlynight.combinechart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.onlynight.chartlibrary.chart.CombineChart;
import com.github.onlynight.chartlibrary.view.CombineChartView;
import com.github.onlynight.combinechart.chart.KLineChartFactory;

public class MainActivity extends AppCompatActivity {

    private CombineChartView mCombineChartView;
    private KLineChartFactory mKLineChartFactory;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKLineChartFactory = new KLineChartFactory(this);

        mCombineChartView = (CombineChartView) findViewById(R.id.chart);
        mCombineChartView.setOperatable(true);
        mCombineChartView.addChart(generateCombineChart1());
        mCombineChartView.addChart(generateCombineChart2());
//        mCombineChartView.addChart(generateCombineChart3());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCombineChartView.setxDelta(0);
                mCombineChartView.invalidate();
            }
        }, 100);
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

}
