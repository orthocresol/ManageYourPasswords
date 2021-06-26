package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VaultActivity extends AppCompatActivity {

    private RecyclerView websites;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);
        setTitle("Logins");
        bottomNavigationView = findViewById(R.id.generatorNavigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        setNavigationBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showList();
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
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
                        intent = new Intent(VaultActivity.this, GeneratorActivityFirebase.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.settings:
                        intent = new Intent(VaultActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }

    public static String getTrackingEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentEmail = auth.getCurrentUser().getEmail();
        currentEmail = currentEmail.substring(0, currentEmail.indexOf('@')).toLowerCase().replace('.', '(');

        return currentEmail;
    }

    public void onAdd_floatingButton(View view) {
        startActivity(new Intent(VaultActivity.this, AddItemActivity.class));
    }

    private void showList() {

        ArrayList<VaultItem> itemList = new ArrayList<>();
        itemList.clear();
        VaultAdapter adapter =  new VaultAdapter(VaultActivity.this, itemList);
        String currentEmail = getTrackingEmail();
        websites = findViewById(R.id.websites);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites").child("Website Names");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    String temp = snapshot1.getValue().toString();
                    itemList.add(new VaultItem());
                    itemList.get(count).setWebName(temp);
                    count++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VaultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference reference1 = db.getReference().child("Users").child(currentEmail).child("Websites").child("Website URL");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    String url = snapshot1.getValue().toString();
                    itemList.get(count).setUrls(url);
                    count++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VaultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        websites.setAdapter(adapter);
        websites.setLayoutManager(new LinearLayoutManager(this));
    }
}
