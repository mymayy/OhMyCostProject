package com.project.ohmycost;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class GraphActivity extends AppCompatActivity {

    Button btnMonth,btnCompare;
    DbPayHelper pDatabaseHelper;
    String mSelected;
    int position=0;
    ArrayList<Integer> mMultiSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        btnMonth = findViewById(R.id.btnMonth);
        btnCompare = findViewById(R.id.btnCompare);
        pDatabaseHelper = new DbPayHelper(this);
        ArrayList<String> listData = GetMonthYear();
        final String[] data = new String[listData.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = listData.get(i);
        }
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                builder.setTitle("Select Month");
                builder.setSingleChoiceItems(data, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        position=i;
                    }
                });
                builder.setPositiveButton("select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelected = data[position];
                        Toast.makeText(getApplicationContext(), "you select " +mSelected, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),PieChartActivity.class);
                        intent.putExtra("month",mSelected);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("back", null);
                builder.create();
                builder.show();

            }
        });
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiSelected = new ArrayList<Integer>();

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(GraphActivity.this);
                builder.setTitle("Select Month");
                builder.setMultiChoiceItems(data, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mMultiSelected.add(which);
                        } else if (mMultiSelected.contains(which)) {
                            mMultiSelected.remove(Integer.valueOf(which));
                        }
                    }
                });

                builder.setPositiveButton("select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text="";
                        for (Integer team : mMultiSelected) {
                            text+=data[team]+" ";
                        }
                        Toast.makeText(getApplicationContext(), "you select" +
                                text, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),BarChartActivity.class);
                        intent.putExtra("month", text);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("back", null);
                builder.create();
                builder.show();

            }
        });


    }

    public ArrayList<String> GetMonthYear() {
        Cursor data = pDatabaseHelper.getMonthYear();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            String month=data.getString(0);
            if(!listData.contains(month))
                listData.add(month);
        }
        return listData;
    }
}