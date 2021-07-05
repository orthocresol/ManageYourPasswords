package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    private EditText newName;
    private EditText newUserEmail;
    private EditText newPassword;
    private EditText newUrl;

    private String prevName;
    private String prevPassword;
    private String prevUserEmail;
    private String prevUrl;

    private String updatedName;
    private String updatedUserEmail;
    private String updatedPassword;
    private String updatedUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setTitle("Update Item");
        showPrevInfo();
    }

    @Override
    public void onBackPressed() {
        bindUpdatedStrings();
        Intent intent = new Intent(UpdateActivity.this, ViewActivity.class);
        intent.putExtra("websiteName", updatedName);
        startActivity(intent);
        finish();
    }

    public void onUpdateItem(View view) {
        bindUpdatedStrings();
        String currentEmail = VaultActivity.getTrackingEmail();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!prevName.equals(updatedName) && snapshot.child("Website Info").hasChild(updatedName)) {
                    Toast.makeText(UpdateActivity.this, "Changed Item already Exits", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child("Website Names").child(prevName).setValue(null);
                    reference.child("Website Names").child(prevName + "URL").setValue(null);
                    reference.child("Website Names").child(updatedName).setValue(updatedName);
                    reference.child("Website Names").child(updatedName + "URL").setValue(updatedUrl);

                    reference.child("Website Info").child(prevName).setValue(null);
                    reference.child("Website Info").child(updatedName).child("Name").setValue(updatedName);
                    reference.child("Website Info").child(updatedName).child("Password").setValue(updatedPassword);
                    reference.child("Website Info").child(updatedName).child("URL").setValue(updatedUrl);
                    reference.child("Website Info").child(updatedName).child("Username Or Email").setValue(updatedUserEmail);

                    Toast.makeText(UpdateActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showPrevInfo() {
        bindLabels();
        getPrevStrings();
        setEditTexts();
    }

    private void setEditTexts() {
        newName.setText(prevName);
        newUserEmail.setText(prevUserEmail);
        newPassword.setText(prevPassword);
        newUrl.setText(prevUrl);
    }

    private void bindLabels() {
        newName = findViewById(R.id.newName);
        newUserEmail = findViewById(R.id.newUserEmail);
        newPassword = findViewById(R.id.newPassword);
        newUrl = findViewById(R.id.newUrl);
    }

    private void getPrevStrings() {
        Intent intent = getIntent();
        prevName = intent.getStringExtra("name");
        prevUserEmail = intent.getStringExtra("userEmail");
        prevPassword = intent.getStringExtra("password");
        prevUrl = intent.getStringExtra("url");
    }

    private void bindUpdatedStrings() {
        updatedName = newName.getText().toString();
        updatedUserEmail = newUserEmail.getText().toString();
        updatedPassword = newPassword.getText().toString();
        updatedUrl = newUrl.getText().toString();
    }
}

