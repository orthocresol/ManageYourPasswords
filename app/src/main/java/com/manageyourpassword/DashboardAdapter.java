package com.manageyourpassword;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    Context context;
    ArrayList<Item> items;
    String identifier;

    public DashboardAdapter(Context context, ArrayList<Item> items, String identifier) {
        this.context = context;
        this.items = items;
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        int id = item.getId();
        String name = item.getName();
        String username = item.getUsername();
        String password = item.getPassword();
        String url = item.getUrl();

        holder.tv_name.setText(name);
        holder.tv_username.setText(username);

        String dest = "https://logo.clearbit.com/" + url;
        Picasso.get()
                .load(dest)
                .placeholder(R.drawable.ic_baseline_web_24)
                .error(R.drawable.ic_baseline_web_24)
                .into(holder.iv_image);
        Picasso.get().setLoggingEnabled(true);

        Log.d("ert", "onBindViewHolder: " + dest);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItemActivitySQL.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("url", url);
                intent.putExtra("identifier", identifier);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_username;
        ImageView iv_image;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.single_row_title);
            tv_username = itemView.findViewById(R.id.single_row_desc);
            iv_image = itemView.findViewById(R.id.single_row_image);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
}
