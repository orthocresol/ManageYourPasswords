package com.manageyourpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class RegisterActivity extends AppCompatActivity {

    CheckBox cb_offline, cb_online;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");

        cb_offline = findViewById(R.id.reg_checkbox_offline);
        cb_online = findViewById(R.id.reg_checkbox_online);

        btn_next = findViewById(R.id.reg_next);

        cb_offline.setChecked(true);

        clickListeners();
    }

    private void clickListeners() {
        cb_offline.setOnClickListener(v -> {
            if(cb_offline.isChecked()){
                cb_online.setChecked(false);
            }
            else {
                cb_online.setChecked(true);
            }
        });

        cb_online.setOnClickListener(v -> {
            if(cb_online.isChecked()){
                cb_offline.setChecked(false);
            }
            else {
                cb_offline.setChecked(true);
            }
        });

        btn_next.setOnClickListener(v -> {
            if(cb_offline.isChecked()){
                startActivity(new Intent(RegisterActivity.this, SignUpActivitySQL.class));
                finish();
            }
            else {
                startActivity(new Intent(RegisterActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, SingleLoginActivity.class));
        finish();
    }
}