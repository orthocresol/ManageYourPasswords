package com.manageyourpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleLoginActivity extends AppCompatActivity {
    Button btn_login;
    MaterialTextView btn_register;

    TextInputLayout lay_email, lay_password;

    EditText et_email;
    EditText et_password;

    String email, password;
    ImageView img_login;
    ProgressBar progressBar;
    SessionManagerSQL sessionManagerSQL;
    Animation top_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_login);
        setTitle("Sign In");

        top_animation = AnimationUtils.loadAnimation(SingleLoginActivity.this ,R.anim.top_animation);

        initVariable();


        String text = "Not a user yet? Register here now!";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.YELLOW);
        ss.setSpan(fcs,15,33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn_register.setText(ss);

        clickListeners();
        disappearVariables();
        if(!autoLoginForSqlite()){
            autoLoginForFirebase();
        }

    }

    private void disappearVariables() {
//        TextView tv = findViewById(R.id.log_tv1);
//        tv.setVisibility(View.INVISIBLE);

        img_login.setVisibility(View.INVISIBLE);
        btn_register.setVisibility(View.INVISIBLE);
        btn_login.setVisibility(View.INVISIBLE);
        et_email.setVisibility(View.INVISIBLE);
        et_password.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        lay_email.setVisibility(View.INVISIBLE);
        lay_password.setVisibility(View.INVISIBLE);

    }

    private void autoLoginForFirebase() {
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
                            startActivity(new Intent(SingleLoginActivity.this, VaultActivity.class));
                            finish();
                        }
                        else {
                            reappearVariables();
                        }
                    }
                    else {
                        reappearVariables();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            reappearVariables();
        }
    }
    private void reappearVariables(){
//        TextView tv = findViewById(R.id.log_tv1);
//        tv.setVisibility(View.VISIBLE);
        img_login.setAnimation(top_animation);
        img_login.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        et_email.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        lay_email.setVisibility(View.VISIBLE);
        lay_password.setVisibility(View.VISIBLE);




    }
    private boolean checkUserState() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }

    private boolean autoLoginForSqlite() {
        if(sessionManagerSQL.getLogin() && sessionManagerSQL.getAutoLogin()){
            Intent intent = new Intent(SingleLoginActivity.this, DashboardActivitySQL.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void clickListeners() {
        btn_login.setOnClickListener(v -> {
            authenticate();
        });

        btn_register.setOnClickListener(v -> {
            btn_register.setTextColor(Color.parseColor("#ffffff"));
            btn_register.setPaintFlags(btn_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            startActivity(new Intent(SingleLoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void authenticate() {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString();

        if (verify()) return;

        if(!trySQLiteAuthentication()){
            tryFirebaseAuthentication();
        }

    }

    private void tryFirebaseAuthentication() {
        progressBar.setVisibility(View.VISIBLE);
        loginUser();
    }

    private void loginUser() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SingleLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SingleLoginActivity.this, VaultActivity.class));
                    finish();
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SingleLoginActivity.this, "Email and password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean verify() {
        if(email.equals("") || password.equals("")){
            Toast.makeText(SingleLoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private boolean trySQLiteAuthentication() {
        DatabaseHelper db = new DatabaseHelper(SingleLoginActivity.this);
        Log.d("debug", "trySQLiteAuthentication: " + email + password);
        Boolean status = db.authenticate(email, password);

        if(status){

            sessionManagerSQL.setLogin(true);
            sessionManagerSQL.setUsername(email);
            Toast.makeText(SingleLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SingleLoginActivity.this, DashboardActivitySQL.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void initVariable() {
        btn_login = findViewById(R.id.log_login_button);
        btn_register = findViewById(R.id.log_register_button);
        et_email = findViewById(R.id.log_email);
        et_password = findViewById(R.id.log_password);
        progressBar = findViewById(R.id.log_progressbar);
        img_login = findViewById(R.id.log_in_icon);
        lay_email = findViewById(R.id.log_email_lay);
        lay_password = findViewById(R.id.log_password_lay);

        progressBar.setVisibility(View.INVISIBLE);
        sessionManagerSQL = new SessionManagerSQL(getApplicationContext());
    }
}