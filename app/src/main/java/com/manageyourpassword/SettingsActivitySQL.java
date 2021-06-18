package com.manageyourpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivitySQL extends AppCompatActivity {
    Button btn_logout;
    SessionManagerSQL sessionManagerSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_sql);

        btn_logout = findViewById(R.id.settingsLogout);
        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagerSQL.setUsername("");
                sessionManagerSQL.setLogin(false);
                Intent intent = new Intent(SettingsActivitySQL.this, LoginActivitySQL.class);
                startActivity(intent);
                finish();
            }
        });
    }
}