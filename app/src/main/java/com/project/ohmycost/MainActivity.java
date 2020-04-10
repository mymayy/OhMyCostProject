package com.project.ohmycost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CalendarView mCalendar;
        Button btnGraph;
        final Button[] btnAdd = new Button[1];
        final TextView textDate;
        final TextView textpayDetail;
        final TextView textList;
        final TextView textTotal;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDate = findViewById(R.id.Date);
        textpayDetail = findViewById(R.id.payDetail);
        textList = findViewById(R.id.list);
        textTotal = findViewById(R.id.total);

        btnGraph = findViewById(R.id.Graph);
        /*btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),GraphActivity.class);
                startActivity(intent1);
            }
        });*/


        mCalendar = findViewById(R.id.calendarView);
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+month+"/"+year;
                textDate.setText(date);

                Bundle bundle =  getIntent().getExtras();
                if (bundle != null){
                    String result = bundle.getString("result");
                    int total = bundle.getInt("total");
                    textpayDetail.setText(result);
                    textTotal.setText("Total = "+total+" Baht");
                    textList.setText("item List :");
                }
                btnAdd[0] = findViewById(R.id.Add);
                btnAdd[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent intent2 = new Intent(getApplicationContext(),AddActivity.class);
                        startActivity(intent2);*/
                    }
                });

            }
        });
    }
}
