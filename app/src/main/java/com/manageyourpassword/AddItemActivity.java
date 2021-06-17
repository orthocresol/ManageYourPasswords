package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddItemActivity extends AppCompatActivity {

    private EditText webName;
    private EditText userEmail;
    private EditText password;
    private EditText url;

    String textWebName;
    String textUserEmail;
    String textPassword;
    String textUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        bindLabels();
    }

    public void onSave(View view) {

        bindStrings();
        String findingEmail = VaultActivity.getTrackingEmail();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(findingEmail).child("Websites");

        reference.child("Website Names").child(textWebName).setValue(textWebName);
        reference.child("Website Info").child(textWebName).child("Name").setValue(textWebName);
        reference.child("Website Info").child(textWebName).child("Username Or Email").setValue(textUserEmail);
        reference.child("Website Info").child(textWebName).child("Password").setValue(textPassword);
        reference.child("Website Info").child(textWebName).child("URL").setValue(textUrl);

        Toast.makeText(AddItemActivity.this, "Data successfully saved", Toast.LENGTH_SHORT).show();
    }

    public void onBack(View view) {
        startActivity(new Intent(AddItemActivity.this, VaultActivity.class));
        finish();
    }

    private void bindLabels() {
        webName = findViewById(R.id.websiteName);
        userEmail = findViewById(R.id.user_email);
        password = findViewById(R.id.webpass);
        url = findViewById(R.id.url);
    }

    private void bindStrings() {
        textWebName = webName.getText().toString();
        textUserEmail = userEmail.getText().toString();
        textPassword = password.getText().toString();
        textUrl = url.getText().toString();
    }
}

