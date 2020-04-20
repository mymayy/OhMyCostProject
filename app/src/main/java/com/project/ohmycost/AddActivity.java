package com.project.ohmycost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    Spinner spinner;
    EditText payInput;
    Button btnAdd,btnViewData,btnBack;
    DbPayHelper pDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pDatabaseHelper = new DbPayHelper(this);
        btnAdd = findViewById(R.id.btn);
        payInput = findViewById(R.id.payInput);
        btnBack = findViewById(R.id.btnBack);
        btnViewData = findViewById(R.id.btnView);
        spinner = findViewById(R.id.spinner);
        ArrayList<String> typeSelect = new ArrayList<>();
        typeSelect.add("Food");
        typeSelect.add("Bus");
        typeSelect.add("Add Type");

        ArrayList<String> typeAdd= (ArrayList<String>)getIntent().getExtras().getSerializable("type");
        if(typeAdd!=null){
            for(int i=0;i<typeAdd.size();i++){
                String type = typeAdd.get(i);
                if(!typeSelect.contains(type))
                    typeSelect.add(typeSelect.size()-1,type);
            }
        }

        ArrayList<String> typeData = GetType();
        for(int i=0;i<typeData.size();i++){
            String type = typeData.get(i);
            if(!typeSelect.contains(type)){
                typeSelect.add(typeSelect.size()-1,type);
            }
        }

        final String day = getIntent().getExtras().getString("day");
        final String monthYear = getIntent().getExtras().getString("month");
        final String Year = getIntent().getExtras().getString("year");
        Toast.makeText(AddActivity.this, day, Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeSelect);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        final ArrayList<String> finalTypeSelect = typeSelect;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Add Type")) {
                    Intent intent1 = new Intent(getApplicationContext(), TypeActivity.class);
                    intent1.putExtra("type", finalTypeSelect);
                    intent1.putExtra("day",day);
                    intent1.putExtra("month",monthYear);
                    intent1.putExtra("year",Year);

                    startActivity(intent1);
                } else {

                    final String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String newEntry = payInput.getText().toString();
                            if (payInput.length() != 0) {
                                AddData(day,monthYear,Year,item,newEntry);
                                payInput.setText("");
                            } else {
                                toastMessage("You must put something in the text field!");
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ListPayActivity.class);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    public void AddData(String day,String month,String year,String item,String amount) {
        boolean insertData = pDatabaseHelper.addData(day,month,year,item,amount);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
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