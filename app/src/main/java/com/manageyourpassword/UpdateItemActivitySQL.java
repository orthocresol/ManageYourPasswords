package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateItemActivitySQL extends AppCompatActivity {
    EditText et_name, et_username, et_password, et_url;
    Button btn_update;
    String name, username, password, url;
    int id;
    String identifier;
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
        identifier = getIntent().getStringExtra("identifier");
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        url = getIntent().getStringExtra("url");
        id = getIntent().getIntExtra("id", 0);

        et_name.setText(name);
        et_username.setText(username);
        et_password.setText(password);
        et_url.setText(url);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update
                name = et_name.getText().toString();
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                url = et_url.getText().toString();

                DatabaseHelper db = new DatabaseHelper(UpdateItemActivitySQL.this);
                db.delete(identifier, id);
                Item item = new Item(-1, name, username, password, url);
                int row_id = db.insert(identifier, item);
                if(row_id != -1){
                    Toast.makeText(UpdateItemActivitySQL.this, "updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateItemActivitySQL.this, ViewItemActivitySQL.class);
                    intent.putExtra("identifier", identifier);
                    intent.putExtra("id", row_id);
                    intent.putExtra("name", name);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(UpdateItemActivitySQL.this, "Updating failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}