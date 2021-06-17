package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPassword;
    private EditText newPassword;
    private EditText confirmedNewPassword;

    String textOldPassword;
    String textNewPassword;
    String textConfirmedNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void onBackToSettings(View view) {
        startActivity(new Intent(ChangePasswordActivity.this, SettingsActivity.class));
        finish();
    }

    public void onResetPassword(View view) {
        bindLabels();
        bindStrings();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String currentEmail = user.getEmail();

        auth.signInWithEmailAndPassword(currentEmail, textOldPassword).addOnCompleteListener(ChangePasswordActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if(textNewPassword.equals(textConfirmedNewPassword)) {
                        user.updatePassword(textNewPassword).addOnCompleteListener(ChangePasswordActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ChangePasswordActivity.this, "Update password failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "New password and Confirmed new password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Email and current password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void bindLabels() {
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmedNewPassword = findViewById(R.id.confirmNewPassword);
    }

    private void bindStrings() {
        textOldPassword = currentPassword.getText().toString();
        textNewPassword = newPassword.getText().toString();
        textConfirmedNewPassword = confirmedNewPassword.getText().toString();
    }
}

