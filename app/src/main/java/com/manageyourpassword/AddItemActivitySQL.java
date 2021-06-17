package com.manageyourpassword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivitySQL extends AppCompatActivity {
    Button btn_add;
    EditText et_name, et_username, et_password, et_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_sql);

        setTitle("Add Item");

        btn_add = findViewById(R.id.addItemAddButton);
        et_name = findViewById(R.id.addItemName);
        et_username = findViewById(R.id.addItemUsername);
        et_password = findViewById(R.id.addItemPassword);
        et_url = findViewById(R.id.addItemUrl);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic to store data
            }
        });

    }
}