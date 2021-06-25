package com.manageyourpassword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat loginState;
    BottomNavigationView bottomNavigationView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        handleLoginState();
        bottomNavigationView = findViewById(R.id.generatorNavigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference();
        String currentEmail = VaultActivity.getTrackingEmail();
        loginState = findViewById(R.id.loginState);
        loginState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reference.child("Users").child(currentEmail).child("Save Login State").setValue("On");
                }
                else {
                    reference.child("Users").child(currentEmail).child("Save Login State").setValue("Off");
                }
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
                        intent = new Intent(SettingsActivity.this, VaultActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.generator:
                        intent = new Intent(SettingsActivity.this, GeneratorActivityFirebase.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }
    private void handleLoginState() {

        String currentEmail = VaultActivity.getTrackingEmail();
        loginState = findViewById(R.id.loginState);
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference reference = db.getReference().child("Users").child(currentEmail);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String textLoginState = snapshot.child("Save Login State").getValue(String.class);
                    if (textLoginState.equals("On")) {
                        loginState.setChecked(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loginState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    reference.child("Users").child(currentEmail).child("Save Login State").setValue("On");
                }
                else {
                    reference.child("Users").child(currentEmail).child("Save Login State").setValue("Off");
                }
            }
        });
    }

    public void onBackVault(View view) {
        startActivity(new Intent(SettingsActivity.this, VaultActivity.class));
        finish();
    }

    public void onLogout(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(SettingsActivity.this, SingleLoginActivity.class));
        finish();
    }

    public void onChangePassword(View view) {
        startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
        finish();
    }
}

