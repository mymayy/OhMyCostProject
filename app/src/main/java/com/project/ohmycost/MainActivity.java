package com.project.ohmycost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CalendarView mCalendar;
    Button btnGraph,btnView;
    private Button[] btnAdd = new Button[1];
    private TextView textDate,textpayDetail,textList,textTotal,textTotalMonth;
    DbPayHelper pDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDate = findViewById(R.id.Date);
        textpayDetail = findViewById(R.id.payDetail);
        textList = findViewById(R.id.list);
        textTotal = findViewById(R.id.total);
        textTotalMonth = findViewById(R.id.totalMonth);
        pDatabaseHelper = new DbPayHelper(this);
        btnGraph = findViewById(R.id.Graph);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),GraphActivity.class);
                startActivity(intent1);
            }
        });

        mCalendar = findViewById(R.id.calendarView);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                final String day = dayOfMonth+"/"+(month+1)+"/"+year;
                final String monthYear = (month+1)+"/"+year;
                final String Year = String.valueOf(year);
                textDate.setText(day);
                textList.setText("item List :");
                String item=GetText(day);
                textpayDetail.setText(item);
                int total=GetTotalDay(day);
                textTotal.setText("Total = "+total+" Baht");
                String totalMonth=GetTotalMonth(monthYear);
                textTotalMonth.setText(totalMonth);
                btnAdd[0] = findViewById(R.id.Add);
                btnAdd[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                        intent.putExtra("day",day);
                        intent.putExtra("month",monthYear);
                        intent.putExtra("year",Year);
                        startActivity(intent);
                    }
                });
                btnView = findViewById(R.id.btnView);
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ListPayActivity.class);
                        intent.putExtra("day",day);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    public String GetText(String date) {
        Cursor data = pDatabaseHelper.getData(date);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1)+"                 "+data.getInt(2));
        }
        String item="";
        for(int i=0;i<=listData.size()-1;i++){
            item+=listData.get(i)+"\n";
        }
        return item;
    }
    public int GetTotalDay(String day) {
        Cursor data = pDatabaseHelper.getAmount(day);
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
    public String GetTotalMonth(String month) {
        Cursor data = pDatabaseHelper.getTotalMonth(month);
        ArrayList<Integer> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getInt(1));
        }
        int total=0;
        for(int i=0;i<=listData.size()-1;i++){
            total+=listData.get(i);
        }
        return "Total = "+total+" Baht";
    }
}