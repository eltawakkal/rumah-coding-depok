package com.example.neardeal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neardeal.DetailStore;
import com.example.neardeal.R;
import com.example.neardeal.response.StoreResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private Context context;
    private List<StoreResponse> listStore;

    public StoreAdapter(Context context, List<StoreResponse> listStore) {
        this.context = context;
        this.listStore = listStore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_store, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTitle.setText(listStore.get(position).getName());
        holder.tvDesc.setText(listStore.get(position).getDescription());

        Picasso.get().load(listStore.get(position).getPhoto()).into(holder.imgStore);

        holder.imgStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailStore.class);
                intent.putExtra("id", listStore.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStore.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStore;
        TextView tvTitle, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgStore = itemView.findViewById(R.id.img_store);
            tvTitle = itemView.findViewById(R.id.tv_store_name);
            tvDesc = itemView.findViewById(R.id.tv_store_desc);

        }
    }
}
