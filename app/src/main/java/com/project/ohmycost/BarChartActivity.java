package com.project.ohmycost;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class BarChartActivity extends AppCompatActivity {

    DbPayHelper pDatabaseHelper;
    BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        pDatabaseHelper = new DbPayHelper(this);
        mChart=findViewById(R.id.bar_chart);

        String monthSelect = getIntent().getExtras().getString("month");
        String[] monthList = monthSelect.split(" ");
        String[] month=new String[monthList.length];
        int[] amount=new int[monthList.length];
        for(int i=0;i<monthList.length;i++){
            String mSelect=monthList[i];
            month[i]=mSelect;
            amount[i]=GetTotalMonth(mSelect);
        }

        ArrayList<BarEntry> data = new ArrayList<>();
        for(int i=0;i<amount.length;i++){
            data.add(new BarEntry(i,amount[i]));
        }
        BarDataSet barDataSet = new BarDataSet(data,"month");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData=new BarData(barDataSet);

        mChart.setFitBars(true);
        mChart.setData(barData);
        mChart.getDescription().setText("Bar Chart");
        mChart.animateXY(3000,5000);

        YAxis leftAxis = mChart.getAxisRight();
        leftAxis.setEnabled(false);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);

        XAxis xAxis=mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(month));
        xAxis.setTextSize(12);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setLabelRotationAngle(45);
    }

    public Integer GetTotalMonth(String month) {
        Cursor data = pDatabaseHelper.getTotalMonth(month);
        ArrayList<Integer> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getInt(1));
        }
        int total=0;
        for(int i=0;i<=listData.size()-1;i++){
            total+=listData.get(i);
        }
        return total;
    }

}
