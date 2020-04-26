package com.project.ohmycost;

//package com.project.ohmycostpay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListTypeActivity extends AppCompatActivity {

    ListView mListView;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_type);
        mListView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);

        ArrayList<String> typeSelect= (ArrayList<String>)getIntent().getExtras().getSerializable("type");
        final ArrayList<String> typeEdit = typeSelect;
        final String day = getIntent().getExtras().getString("day");
        final String monthYear = getIntent().getExtras().getString("month");
        final String Year = getIntent().getExtras().getString("year");

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, typeEdit);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(ListTypeActivity.this, EditTypeActivity.class);
                intent.putExtra("typeEdit",type);
                intent.putExtra("type",typeEdit);
                intent.putExtra("day",day);
                intent.putExtra("month",monthYear);
                intent.putExtra("year",Year);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTypeActivity.this, TypeActivity.class);
                intent.putExtra("type",typeEdit);
                intent.putExtra("day",day);
                intent.putExtra("month",monthYear);
                intent.putExtra("year",Year);
                startActivity(intent);
            }
        });

    }
}
