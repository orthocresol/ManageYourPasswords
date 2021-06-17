package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VaultActivity extends AppCompatActivity {

    private ListView websites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);
        showList();
    }

    public static String getTrackingEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentEmail = auth.getCurrentUser().getEmail();
        currentEmail = currentEmail.substring(0, currentEmail.indexOf('@')).toLowerCase().replace('.', '(');

        return currentEmail;
    }

    public void onAdd(View view) {
        startActivity(new Intent(VaultActivity.this, AddItemActivity.class));
        finish();
    }

    public void onSettings(View view) {
        startActivity(new Intent(VaultActivity.this, SettingsActivity.class));
        finish();
    }

    private void showList() {

        String currentEmail = getTrackingEmail();

        websites = findViewById(R.id.websites);
        final ArrayList<String> websiteList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(VaultActivity.this, android.R.layout.simple_list_item_1, websiteList);
        websites.setAdapter(adapter);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites").child("Website Names");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                websiteList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    websiteList.add(snapshot1.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VaultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        websites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String websiteName = websiteList.get(position);
                Intent intent = new Intent(VaultActivity.this, ViewActivity.class);
                intent.putExtra("websiteName", websiteName);
                startActivity(intent);
                finish();
            }
        });

    }
}
