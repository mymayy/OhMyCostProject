package com.project.ohmycost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditTypeActivity extends AppCompatActivity {

    private Button btnSave,btnDelete,btnBack;
    private EditText editable_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_type);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        editable_item = findViewById(R.id.editable_item);

        final ArrayList<String> typeEdit= (ArrayList<String>)getIntent().getExtras().getSerializable("type");
        final String type=getIntent().getExtras().getString("typeEdit");
        final String day = getIntent().getExtras().getString("day");
        final String monthYear = getIntent().getExtras().getString("month");
        final String Year = getIntent().getExtras().getString("year");
        editable_item.setText(type);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    for(int i=0;i<typeEdit.size();i++){
                        if(typeEdit.get(i).equals(type)){
                            typeEdit.remove(i);
                            typeEdit.add(i,item);
                            toastMessage("Type Successfully Edit!");
                        }
                    }
                }else{
                    toastMessage("You must enter a type");
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeEdit.remove(type);
                editable_item.setText("");
                toastMessage("removed type");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTypeActivity.this, ListTypeActivity.class);
                intent.putExtra("type",typeEdit);
                intent.putExtra("day",day);
                intent.putExtra("month",monthYear);
                intent.putExtra("year",Year);
                startActivity(intent);
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
