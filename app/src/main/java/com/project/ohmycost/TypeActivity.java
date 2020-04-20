package com.project.ohmycost;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TypeActivity extends AppCompatActivity {

    Button btnAdd,btnBack;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        final ArrayList<String> typeSelect= (ArrayList<String>)getIntent().getExtras().getSerializable("type");
        final String day = getIntent().getExtras().getString("day");
        final String monthYear = getIntent().getExtras().getString("month");
        final String Year = getIntent().getExtras().getString("year");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    typeSelect.add(typeSelect.size()-1,newEntry);
                    editText.setText("");
                    toastMessage("Data Successfully Inserted!");
                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });

        final String[] data = new String[typeSelect.size()-1];
        for (int i = 2; i < data.length-1; i++) {
            data[i] = typeSelect.get(i);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("type", typeSelect);
                intent.putExtra("day",day);
                intent.putExtra("month",monthYear);
                intent.putExtra("year",Year);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}

