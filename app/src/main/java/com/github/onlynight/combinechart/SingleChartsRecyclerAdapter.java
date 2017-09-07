package com.github.onlynight.combinechart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.onlynight.chartlibrary.chart.impl.CombineChart;
import com.github.onlynight.chartlibrary.view.SingleCombineChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/8/28.
 */

public class SingleChartsRecyclerAdapter extends RecyclerView.Adapter<SingleChartsRecyclerAdapter.ViewHolder> {

    private List<CombineChart> mCharts;

    public SingleChartsRecyclerAdapter() {
        mCharts = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_single_chart, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CombineChart chart = mCharts.get(position);
        holder.singleChartView.setChart(chart);
        holder.singleChartView.setCanOperate(false);
    }

    @Override
    public int getItemCount() {
        return mCharts.size();
    }

    public void addData(CombineChart chart) {
        if (chart != null) {
            mCharts.add(chart);
        }
    }

    public void clearData() {
        mCharts.clear();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SingleCombineChartView singleChartView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.singleChartView = (SingleCombineChartView) itemView.findViewById(R.id.chartView);
        }

    }

}
