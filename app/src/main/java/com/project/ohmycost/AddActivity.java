package com.project.ohmycost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText payInput;
    Button btnAdd,btnViewData,btnBack,btnSelect;
    TextView textSelect;
    DbPayHelper pDatabaseHelper;
    int position=0;
    String mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pDatabaseHelper = new DbPayHelper(this);
        btnAdd = findViewById(R.id.btn);
        payInput = findViewById(R.id.payInput);
        btnBack = findViewById(R.id.btnBack);
        btnViewData = findViewById(R.id.btnView);
        btnSelect = findViewById(R.id.btnSelect);
        textSelect = findViewById(R.id.textSelect);
        final ArrayList<String> typeSelect = new ArrayList<>();
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

        final String[] data = new String[typeSelect.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = typeSelect.get(i);
        }

        final String day = getIntent().getExtras().getString("day");
        final String monthYear = getIntent().getExtras().getString("month");
        final String Year = getIntent().getExtras().getString("year");
        final ArrayList<String> typeMonth = GetTypeMonth(day);
        toastMessage(day);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("Select Type");
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
                        if (mSelected.equals("Add Type")) {
                            Intent intent1 = new Intent(getApplicationContext(), TypeActivity.class);
                            typeSelect.remove("Add Type");
                            intent1.putExtra("type", typeSelect);
                            intent1.putExtra("day",day);
                            intent1.putExtra("month",monthYear);
                            intent1.putExtra("year",Year);
                            startActivity(intent1);
                        }else
                            textSelect.setText(mSelected);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("back", null);
                builder.create();
                builder.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newEntry = payInput.getText().toString();
                if(textSelect.length()!=0){
                    if(typeMonth.contains(mSelected)){
                        toastMessage("Click VIEW DATA to edit data of this type.");
                    }else{
                        if (payInput.length() != 0) {
                            AddData(day,monthYear,Year,mSelected,newEntry);
                            payInput.setText("");
                            textSelect.setText("");
                            typeMonth.add(mSelected);
                        } else {
                            toastMessage("You must put something in the text field!");
                        }
                    }
                }else{
                    toastMessage("Please select type.");
                }
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

    public ArrayList<String> GetTypeMonth(String day) {
        Cursor data = pDatabaseHelper.getAmount(day);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(0));
        }
        return listData;
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}