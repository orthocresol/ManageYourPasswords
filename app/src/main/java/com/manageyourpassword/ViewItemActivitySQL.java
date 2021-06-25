package com.manageyourpassword;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewItemActivitySQL extends AppCompatActivity {
    ImageButton btn_update, btn_delete;
    TextView tv_name, tv_username, tv_password, tv_url;
    ImageView iv_logo;
    String identifier;
    int id;
    AlertDialog dialog;
    String name, username, password, url;
    ImageButton btn_copy, btn_copy_password, btn_launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_sql);
        setTitle("View Item");

        identifier = getIntent().getStringExtra("identifier");
        btn_update = findViewById(R.id.updateF);
        btn_delete = findViewById(R.id.Delete);
        tv_name = findViewById(R.id.Name);
        tv_username = findViewById(R.id.User_email);
        tv_password = findViewById(R.id.Webpass);
        tv_url = findViewById(R.id.Url);
        iv_logo = findViewById(R.id.viewItemLogo);
        id = getIntent().getIntExtra("id", 0);
        btn_copy = findViewById(R.id.viewItemCopy);
        btn_copy_password = findViewById(R.id.viewItemCopyPassword);
        btn_launch = findViewById(R.id.viewItemLaunch);

        clickListener();
        setAttribute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void setAttribute() {
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        url = getIntent().getStringExtra("url");
        tv_name.setText(name);
        tv_username.setText(username);
        tv_password.setText(password);
        tv_url.setText(url);

        String dest = "https://logo.clearbit.com/" + url;
        Picasso.get()
                .load(dest)
                .placeholder(R.drawable.ic_baseline_web_24)
                .error(R.drawable.ic_baseline_web_24)
                .into(iv_logo);
        Picasso.get().setLoggingEnabled(true);
    }

    private void clickListener() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete entry
                dialog = new AlertDialog.Builder(ViewItemActivitySQL.this)
                        .setMessage("Do you really want to delete this item?")
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(ViewItemActivitySQL.this);
                        boolean b = db.delete(identifier, id);
                        //unsure
                        /*if(b){
                            Toast.makeText(ViewItemActivitySQL.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(ViewItemActivitySQL.this, "error occured during deletion", Toast.LENGTH_SHORT).show();
                        }*/
                        Toast.makeText(ViewItemActivitySQL.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewItemActivitySQL.this, UpdateItemActivitySQL.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("url", url);
                intent.putExtra("identifier", identifier);
                startActivity(intent);
                finish();
                //putextra
            }
        });

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("username", tv_username.getText().toString());
                clipboard.setPrimaryClip(clip);

                clip.getDescription();
                Toast.makeText(ViewItemActivitySQL.this, "Username copied", Toast.LENGTH_SHORT).show();
            }
        });

        btn_copy_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", tv_password.getText().toString());
                clipboard.setPrimaryClip(clip);

                clip.getDescription();
                Toast.makeText(ViewItemActivitySQL.this, "Password copied", Toast.LENGTH_SHORT).show();
            }
        });

        btn_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(tv_url.getText().toString().trim());
            }
        });
    }

    private void goToUrl(String toString) {
        toString = "https://" + toString;
        Uri uri = Uri.parse(toString);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}