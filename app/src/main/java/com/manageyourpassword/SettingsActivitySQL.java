package com.manageyourpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivitySQL extends AppCompatActivity {
    Button btn_logout;
    SessionManagerSQL sessionManagerSQL;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_sql);

        setTitle("Settings");

        bottomNavigationView = findViewById(R.id.generatorNavigation);
        btn_logout = findViewById(R.id.settingsLogout);
        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());
        bottomNavigationView.setSelectedItemId(R.id.settings);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagerSQL.setUsername("");
                sessionManagerSQL.setLogin(false);
                Intent intent = new Intent(SettingsActivitySQL.this, LoginMergedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setNavigationBar();
    }
    private void setNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.settings:
                        return true;
                    case R.id.dashboard:
                        intent = new Intent(SettingsActivitySQL.this, DashboardActivitySQL.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.generator:
                        intent = new Intent(SettingsActivitySQL.this, GeneratorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }
}