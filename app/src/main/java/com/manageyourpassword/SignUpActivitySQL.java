package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivitySQL extends AppCompatActivity {
    Button btn_login, btn_register;
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sql);
        setTitle("Sign up");

        btn_login = findViewById(R.id.register_login);
        btn_register = findViewById(R.id.register_register);
        et_username = findViewById(R.id.register_email);
        et_password = findViewById(R.id.register_password);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new account
                // validate function
                String username, password;
                username = et_username.getText().toString().trim().toLowerCase();
                password = et_password.getText().toString().trim();

                validate(username, password);

                DatabaseHelper db = new DatabaseHelper(SignUpActivitySQL.this);
                boolean status = db.register(username, password);
                if(status){
                    Toast.makeText(SignUpActivitySQL.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    db.createTableForUser(username);



                    Intent intent = new Intent(SignUpActivitySQL.this, DashboardActivitySQL.class);
                    intent.putExtra("identifier", username);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignUpActivitySQL.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivitySQL.this, LoginActivitySQL.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validate(String username, String password) {
        if(username.length() == 0){
            Toast.makeText(SignUpActivitySQL.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if(password.length() == 0){
            Toast.makeText(SignUpActivitySQL.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}