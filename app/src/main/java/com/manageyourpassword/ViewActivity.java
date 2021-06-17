package com.manageyourpassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ViewActivity extends AppCompatActivity {

    private TextView name;
    private TextView userEmail;
    private TextView password;
    private TextView url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        showInfo();
    }

    public void onBackClick(View view) {
        startActivity(new Intent(ViewActivity.this, VaultActivity.class));
        finish();
    }

    public void onChange(View view) {

        String websiteName = getIntentString();
        String currentEmail = VaultActivity.getTrackingEmail();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites").child("Website Info").child(websiteName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String nameFromDb = snapshot.child("Name").getValue(String.class);
                    String passwordFromDb = snapshot.child("Password").getValue(String.class);
                    String urlFromDb = snapshot.child("URL").getValue(String.class);
                    String userOrEmail = snapshot.child("Username Or Email").getValue(String.class);

                    Intent intent =  new Intent(ViewActivity.this, UpdateActivity.class);
                    intent.putExtra("name", nameFromDb);
                    intent.putExtra("userEmail", userOrEmail);
                    intent.putExtra("password", passwordFromDb);
                    intent.putExtra("url", urlFromDb);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(ViewActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDeleteItem(View view) {

        String webSiteName = getIntentString();
        String currentEmail = VaultActivity.getTrackingEmail();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites");
        reference.child("Website Info").child(webSiteName).setValue(null);
        reference.child("Website Names").child(webSiteName).setValue(null);

        startActivity(new Intent(ViewActivity.this, VaultActivity.class));
        finish();
    }

    private void showInfo() {

        bindLabels();
        String websiteName = getIntentString();
        String currentEmail = VaultActivity.getTrackingEmail();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference().child("Users").child(currentEmail).child("Websites").child("Website Info").child(websiteName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String nameFromDb = snapshot.child("Name").getValue(String.class);
                    String passwordFromDb = snapshot.child("Password").getValue(String.class);
                    String urlFromDb = snapshot.child("URL").getValue(String.class);
                    String userOrEmail = snapshot.child("Username Or Email").getValue(String.class);

                    name.setText("Name: " + nameFromDb);
                    userEmail.setText("Username/Email: " + userOrEmail);
                    password.setText("Password: " + passwordFromDb);
                    url.setText("URL: " + urlFromDb);
                }
                else {
                    Toast.makeText(ViewActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindLabels() {

        name = findViewById(R.id.Name);
        userEmail = findViewById(R.id.User_email);
        password = findViewById(R.id.Webpass);
        url = findViewById(R.id.Url);
    }

    private String getIntentString() {
        Intent intent = getIntent();
        String intentString = intent.getStringExtra("websiteName");
        return intentString;
    }
}

