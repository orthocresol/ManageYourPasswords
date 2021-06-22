package com.manageyourpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.PropertyResourceBundle;

public class LoginMergedActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnLogin, btn_register;
    EditText et_email, et_password;
    SessionManagerSQL sessionManagerSQL;
    TextView tv1, tv2;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_merged);
        setTitle("Login");

        radioGroup = findViewById(R.id.mergedRadioGroup);
        btnLogin = findViewById(R.id.mergedLogin);
        btn_register = findViewById(R.id.mergedRegister);
        et_email = findViewById(R.id.loginMergedEmail);
        et_password = findViewById(R.id.loginMergedPassword);
        tv1 = findViewById(R.id.loginMergedTv);
        tv2 = findViewById(R.id.mergedNewUser);
        progressBar = findViewById(R.id.mergedProgressbar);

        radioGroup.setVisibility(View.INVISIBLE);
        btn_register.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
        et_email.setVisibility(View.INVISIBLE);
        et_password.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());

        sqliteAutoLogin();
        firebaseAutoLogin();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginMergedActivity.this, SignUpMergedActivity.class));
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = radioGroup.findViewById(radioID);
                String text = (String) radioButton.getText();
                Log.d("tag2", "onClick: " + text);
                if(text.equals("Online")){
                    Intent intent = new Intent(LoginMergedActivity.this, LoginActivity.class);
                    intent.putExtra("username", et_email.getText().toString().trim());
                    intent.putExtra("password", et_password.getText().toString());
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(LoginMergedActivity.this, LoginActivitySQL.class);
                    intent.putExtra("username", et_email.getText().toString().trim());
                    intent.putExtra("password", et_password.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void sqliteAutoLogin() {
        if(sessionManagerSQL.getLogin()){
            Intent intent = new Intent(LoginMergedActivity.this, DashboardActivitySQL.class);
            intent.putExtra("identifier", sessionManagerSQL.getUsername());
            startActivity(intent);
            finish();
        }
    }

    private void firebaseAutoLogin() {
        autoLogin();
    }

    private void autoLogin() {
        if(checkUserState()) {
            String currentEmail = VaultActivity.getTrackingEmail();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference reference = db.getReference().child("Users").child(currentEmail);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String textLoginState = snapshot.child("Save Login State").getValue(String.class);
                        if (textLoginState.equals("On")) {
                            startActivity(new Intent(LoginMergedActivity.this, VaultActivity.class));
                            finish();
                        }
                    }
                    else {
                        radioGroup.setVisibility(View.VISIBLE);
                        btn_register.setVisibility(View.VISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        et_email.setVisibility(View.VISIBLE);
                        et_password.setVisibility(View.VISIBLE);
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            radioGroup.setVisibility(View.VISIBLE);
            btn_register.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            et_email.setVisibility(View.VISIBLE);
            et_password.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkUserState() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }

    public void checkButton(View v){


    }
}