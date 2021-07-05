package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
        setTitle("Add Item");
        bindLabels();
    }

    public void onSave(View view) {

        bindStrings();

        if(verified(textWebName,textUserEmail, textPassword, textUrl)) {
            String findingEmail = VaultActivity.getTrackingEmail();

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference reference = db.getReference().child("Users").child(findingEmail).child("Websites");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Website Info").hasChild(textWebName)) {
                        Toast.makeText(AddItemActivity.this, "Item Already Exits", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        reference.child("Website Names").child(textWebName).setValue(textWebName);
                        reference.child("Website Names").child(textWebName + "URL").setValue(textUrl);

                        reference.child("Website Info").child(textWebName).child("Name").setValue(textWebName);
                        reference.child("Website Info").child(textWebName).child("Username Or Email").setValue(textUserEmail);
                        reference.child("Website Info").child(textWebName).child("Password").setValue(textPassword);
                        reference.child("Website Info").child(textWebName).child("URL").setValue(textUrl);

                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddItemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    private boolean verified(String textName, String textEmail, String textPassword, String textUrl) {

        if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textName)
                || TextUtils.isEmpty(textUrl)) {
            Toast.makeText(AddItemActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

