package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivitySQL extends AppCompatActivity {
    Button btn_login, btn_register;
    EditText et_username, et_password;
    SessionManagerSQL sessionManagerSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sql);
        setTitle("Login");

        et_username = findViewById(R.id.login_email);
        et_password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_login);
        btn_register = findViewById(R.id.login_register);
        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());


        clickListener();

        if(sessionManagerSQL.getLogin()){
            Intent intent = new Intent(LoginActivitySQL.this, DashboardActivitySQL.class);
            intent.putExtra("identifier", sessionManagerSQL.getUsername());
            startActivity(intent);
            finish();
        }
    }

    //this is a comment;

    private void clickListener() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivitySQL.this, SignUpActivitySQL.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //authentication
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString();
                //username cannot contain dots -> implement
                if (authenticateUser(username, password)) return;

                DatabaseHelper db = new DatabaseHelper(LoginActivitySQL.this);
                Boolean status = db.authenticate(username, password);
                if(status){
                    sessionManagerSQL.setLogin(true);
                    sessionManagerSQL.setUsername(username);
                    Toast.makeText(LoginActivitySQL.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivitySQL.this, DashboardActivitySQL.class);
                    //intent.putExtra("identifier", username);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivitySQL.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                
                
            }
        });
    }

    private boolean authenticateUser(String username, String password) {
        if(password.equals("")){
            et_password.setError("Please enter password");
            return true;
        }
        if(username.equals("")){
            et_username.setError("Please enter username");
            return true;
        }
        return false;
    }
}