package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivitySQL extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Button temporary_generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sql);
        setTitle("Dashboard");

        floatingActionButton = findViewById(R.id.dashboard_floating_button);
        temporary_generator = findViewById(R.id.dashboard_generator);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySQL.this, AddItemActivitySQL.class);
                startActivity(intent);
            }
        });

        temporary_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySQL.this, GeneratorActivity.class);
                startActivity(intent);
            }
        });

    }
}