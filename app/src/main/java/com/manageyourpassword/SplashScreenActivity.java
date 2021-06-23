package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Button btn_proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        btn_proceed = findViewById(R.id.splash_proceed);


        btn_proceed.setOnClickListener(v -> {
            startActivity(new Intent(SplashScreenActivity.this, SingleLoginActivity.class));
            finish();
        });
    }
}