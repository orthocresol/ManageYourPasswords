package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivitySQL extends AppCompatActivity {
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sql);
        setTitle("Dashboard");

        floatingActionButton = findViewById(R.id.dashboard_floating_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySQL.this, AddItemActivitySQL.class);
                startActivity(intent);
            }
        });

    }
}