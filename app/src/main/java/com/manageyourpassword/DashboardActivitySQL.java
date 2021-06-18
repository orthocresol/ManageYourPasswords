package com.manageyourpassword;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivitySQL extends AppCompatActivity {
    FloatingActionButton floatingActionButton;

    String identifier;
    RecyclerView recyclerView;
    DashboardAdapter adapter;
    DatabaseHelper db;
    BottomNavigationView bottomNavigationView;
    SessionManagerSQL sessionManagerSQL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sql);
        setTitle("Dashboard");

        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());
        floatingActionButton = findViewById(R.id.dashboard_floating_button);
        bottomNavigationView = findViewById(R.id.generatorNavigation);
        recyclerView = findViewById(R.id.dashboard_recyclerview);
        identifier = sessionManagerSQL.getUsername();
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        clickListener();
        db = new DatabaseHelper(DashboardActivitySQL.this);
        ArrayList<Item> items = db.getEveryone(identifier);
        Log.d("TAG", "onCreate: ");
        adapter = new DashboardAdapter(DashboardActivitySQL.this, items, identifier);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setNavigationBar();


    }

    private void setNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.generator:
                        intent = new Intent(DashboardActivitySQL.this, GeneratorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.settings:
                        intent = new Intent(DashboardActivitySQL.this, SettingsActivitySQL.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        Log.d("TAG4", "onStart: " + identifier);
        ArrayList<Item> items = db.getEveryone(identifier);

        adapter = new DashboardAdapter(DashboardActivitySQL.this, items, identifier);
        recyclerView.setAdapter(adapter);
    }

    private void clickListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySQL.this, AddItemActivitySQL.class);
                intent.putExtra("identifier", identifier);
                startActivity(intent);
            }
        });
    }
}