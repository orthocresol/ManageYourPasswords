package com.manageyourpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;
    Animation top_animation, bottom_animation;
    ImageView img;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        top_animation = AnimationUtils.loadAnimation(FirstScreenActivity.this ,R.anim.top_animation);
        bottom_animation = AnimationUtils.loadAnimation(FirstScreenActivity.this, R.anim.bottom_animation);

        img = findViewById(R.id.main_imageview);
        slogan = findViewById(R.id.main_slogan);

        img.setAnimation(top_animation);
        slogan.setAnimation(bottom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstScreenActivity.this, SingleLoginActivity.class);
                startActivity(intent);

            }
        }, SPLASH_SCREEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}