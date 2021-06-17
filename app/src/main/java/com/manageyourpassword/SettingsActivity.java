package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onBackVault(View view) {
        startActivity(new Intent(SettingsActivity.this, VaultActivity.class));
        finish();
    }

    public void onLogout(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
        finish();
    }

    public void onChangePassword(View view) {
        startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
        finish();
    }
}

