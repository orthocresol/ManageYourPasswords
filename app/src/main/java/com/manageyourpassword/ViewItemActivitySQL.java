package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItemActivitySQL extends AppCompatActivity {
    Button btn_update, btn_delete;
    TextView tv_name, tv_username, tv_password, tv_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_sql);
        setTitle("View Item");

        btn_update = findViewById(R.id.viewItemUpdate);
        btn_delete = findViewById(R.id.viewItemDelete);
        tv_name = findViewById(R.id.viewItemName);
        tv_username = findViewById(R.id.viewItemUsername);
        tv_password = findViewById(R.id.viewItemPassword);
        tv_url = findViewById(R.id.viewItemUrl);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete entry
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewItemActivitySQL.this, UpdateItemActivitySQL.class);
                startActivity(intent);
                //putextra
            }
        });
    }
}