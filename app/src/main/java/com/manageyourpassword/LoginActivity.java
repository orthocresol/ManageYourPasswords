package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    String textEmail;
    String textPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLogin();
        setContentView(R.layout.activity_login);
    }

    public void onLogin(View view) {
        bindLabels();
        bindStrings();
        if(!TextUtils.isEmpty(textEmail) && !TextUtils.isEmpty(textPassword)) {
            loginUser(textEmail, textPassword);
        }
        else {
            Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
        }
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
                    finish();
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
                            startActivity(new Intent(LoginActivity.this, VaultActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean checkUserState() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }
}
