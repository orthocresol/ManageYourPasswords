package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    String textEmail;
    String textPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLabels();
    }

    public void onLogin(View view) {
        bindStrings();
        loginUser(textEmail, textPassword);
    }

    public void onRegister(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

    private void loginUser(String textEmail, String textPassword) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, VaultActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Email and password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindLabels() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    private void bindStrings() {
        textEmail = email.getText().toString();
        textPassword = password.getText().toString();
    }
}
