package com.manageyourpassword;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivitySQL extends AppCompatActivity {
    Button btn_add;
    EditText et_name, et_username, et_password, et_url;
    String identifier;

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
        identifier = getIntent().getStringExtra("identifier");

        clickListener();

    }

    private void clickListener() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logic to store data
                String name, username, password, url;
                //validate();

                name = et_name.getText().toString().trim();
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString();
                url = et_url.getText().toString().trim();

                if(name.length() == 0){
                    Toast.makeText(AddItemActivitySQL.this, "Name field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Item item = new Item(-1, name, username, password, url);
                DatabaseHelper db = new DatabaseHelper(AddItemActivitySQL.this);
                boolean b = db.insert(identifier, item);
                if(b){
                    Toast.makeText(AddItemActivitySQL.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(AddItemActivitySQL.this, "Item adding fail", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}