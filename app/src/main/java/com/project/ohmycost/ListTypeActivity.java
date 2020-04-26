package com.project.ohmycost;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListTypeActivity extends AppCompatActivity {

    ListView mListView;
    Button btnBack;
    DbPayHelper pDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_type);
        mListView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);
        pDatabaseHelper = new DbPayHelper(this);

        final ArrayList<String> typeData = GetType();

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
                if(typeData.contains(type)){
                    toastMessage("This type is in used. Can't delete or edit.");
                }else if(type.equals("Food") || type.equals("Bus")){
                    toastMessage("This is fixed type. Can't delete or edit.");
                } else{
                    Intent intent = new Intent(ListTypeActivity.this, EditTypeActivity.class);
                    intent.putExtra("typeEdit",type);
                    intent.putExtra("type",typeEdit);
                    intent.putExtra("day",day);
                    intent.putExtra("month",monthYear);
                    intent.putExtra("year",Year);
                    startActivity(intent);
                }
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
    public ArrayList<String> GetType() {
        Cursor data = pDatabaseHelper.getType();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            String type=data.getString(0);
            if(!listData.contains(type))
                listData.add(type);
        }
        return listData;
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
