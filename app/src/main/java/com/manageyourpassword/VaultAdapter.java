package com.manageyourpassword;

import android.content.Context;
import android.content.Intent;
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

public class VaultAdapter extends RecyclerView.Adapter<VaultAdapter.ViewHolder> {

    Context context;
    ArrayList<VaultItem> websiteNames;

    public VaultAdapter(Context context, ArrayList<VaultItem> websiteNames) {
        this.context = context;
        this.websiteNames = websiteNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_row_vault, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String url = websiteNames.get(position).getUrls();
        String webName = websiteNames.get(position).getWebName();
        holder.webName.setText(webName);

        String dest = "https://logo.clearbit.com/" + url;
        Picasso.get()
                .load(dest)
                .placeholder(R.drawable.ic_baseline_web_24)
                .error(R.drawable.ic_baseline_web_24)
                .into(holder.webImage);
        Picasso.get().setLoggingEnabled(true);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("websiteName", webName);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return websiteNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView webImage;
        private TextView webName;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webImage = itemView.findViewById(R.id.single_row_image_vault);
            webName = itemView.findViewById(R.id.single_row_title_vault);
            constraintLayout = itemView.findViewById(R.id.vaultLayout);
        }
    }
}
