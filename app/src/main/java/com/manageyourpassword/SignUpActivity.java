package com.manageyourpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    String textName;
    String textEmail;
    String textPassword;
    String textConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bindLabels();
    }

    public void onSignUp(View view) {
        bindStrings();
        if(verified(textName, textEmail, textPassword, textConfirmPassword)) {
            signUpUser(textName, textEmail, textPassword);
        }
    }

    private boolean verified(String textName, String textEmail, String textPassword, String textConfirmPassword) {

        if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textName)) {
            Toast.makeText(SignUpActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(textPassword.length() < 6) {
            Toast.makeText(SignUpActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!textPassword.equals(textConfirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Confirmed Password does not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signUpUser(String _name, String _email, String _password) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    String storingEmailString = _email.substring(0, _email.indexOf('@')).toLowerCase().replace('.', '(');
                    DatabaseReference reference = db.getReference().child("Users").child(storingEmailString);
                    reference.child("Name").setValue(_name);
                    startActivity(new Intent(SignUpActivity.this, VaultActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindLabels() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        name = findViewById(R.id.name);
    }

    private void bindStrings() {
        textName = name.getText().toString();
        textEmail = email.getText().toString();
        textPassword = password.getText().toString();
        textConfirmPassword = confirmPassword.getText().toString();
    }
}
