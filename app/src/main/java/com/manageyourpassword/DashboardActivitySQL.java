package com.manageyourpassword;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivitySQL extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Button temporary_generator;
    String identifier;
    RecyclerView recyclerView;
    DashboardAdapter adapter;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sql);
        setTitle("Dashboard");

        floatingActionButton = findViewById(R.id.dashboard_floating_button);
        temporary_generator = findViewById(R.id.dashboard_generator);
        recyclerView = findViewById(R.id.dashboard_recyclerview);
        identifier = getIntent().getStringExtra("identifier");

        clickListener();
        db = new DatabaseHelper(DashboardActivitySQL.this);
        ArrayList<Item> items = db.getEveryone(identifier);
        adapter = new DashboardAdapter(DashboardActivitySQL.this, items, identifier);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: " + identifier);
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

        temporary_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivitySQL.this, GeneratorActivity.class);
                startActivity(intent);
            }
        });
    }
}