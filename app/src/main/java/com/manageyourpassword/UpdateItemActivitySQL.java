package com.manageyourpassword;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateItemActivitySQL extends AppCompatActivity {
    EditText et_name, et_username, et_password, et_url;
    Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_sql);
        setTitle("Update Item");

        et_name = findViewById(R.id.updateItemName);
        et_username = findViewById(R.id.updateItemUsername);
        et_password = findViewById(R.id.updateItemPassword);
        et_url = findViewById(R.id.updateItemUrl);
        btn_update = findViewById(R.id.updateItemUpdateButton);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update
            }
        });

    }
}