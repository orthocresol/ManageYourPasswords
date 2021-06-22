package com.manageyourpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpMergedActivity extends AppCompatActivity {


    CheckBox checkBoxOnline;
    CheckBox checkBoxOffline;
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_merged);

        checkBoxOnline = findViewById(R.id.signup_checkbox_Online);
        checkBoxOffline = findViewById(R.id.signup_checkbox_Offline);
        btn_next = findViewById(R.id.sign_up_button_next);

        checkBoxOffline.setChecked(true);

       checkBoxOffline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(checkBoxOffline.isChecked()){
                   checkBoxOnline.setChecked(false);
               }
           }
       });

        checkBoxOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxOnline.isChecked()){
                    checkBoxOffline.setChecked(false);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxOnline.isChecked()){
                    Intent intent = new Intent(SignUpMergedActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(checkBoxOffline.isChecked()){
                    Intent intent = new Intent(SignUpMergedActivity.this, SignUpActivitySQL.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignUpMergedActivity.this, "Please select one of the databases", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpMergedActivity.this, LoginMergedActivity.class);
        startActivity(intent);
        finish();
    }
}