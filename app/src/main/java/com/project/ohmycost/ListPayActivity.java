package com.project.ohmycost;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListPayActivity extends AppCompatActivity {


    private static final String TAG = "ListPayActivity";
    DbPayHelper pDatabaseHelper;
    ListView mListView;
    Button btnBackMain,btnBackAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pay);
        mListView = findViewById(R.id.listView);
        btnBackMain = findViewById(R.id.btnBackMain);
        btnBackAdd = findViewById(R.id.btnBackAdd);
        pDatabaseHelper = new DbPayHelper(this);
        final String day = getIntent().getExtras().getString("day");

        populateListView();

        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPayActivity.this, AddActivity.class);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        final String day = getIntent().getExtras().getString("day");
        Cursor data = pDatabaseHelper.getData(day);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1)+"              "+data.getInt(2));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                String[] arr = text.split("              ");
                String amount = arr[1];
                Log.d(TAG, "onItemClick: You Clicked on " + amount);

                Cursor data = pDatabaseHelper.getItemID(day,amount);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListPayActivity.this, EditPayActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",amount);
                    editScreenIntent.putExtra("day",day);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
