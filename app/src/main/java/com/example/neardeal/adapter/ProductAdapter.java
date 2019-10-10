package com.example.neardeal.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neardeal.DetailProductActivity;
import com.example.neardeal.R;
import com.example.neardeal.response.DealResponse;
import com.example.neardeal.response.ProductResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductResponse> listResponse;

    public ProductAdapter(Context context, List<ProductResponse> listResponse) {
        this.context = context;
        this.listResponse = listResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProdName.setText(listResponse.get(position).getName());
        holder.tvStoreName.setText(listResponse.get(position).getStoreName());
        holder.tvOriPrice.setText(listResponse.get(position).getPrice());
        holder.tvDesc.setText(listResponse.get(position).getDescription());

        Picasso.get().load(listResponse.get(position).getPhoto()).into(holder.imgProd);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("code", 1);
                intent.putExtra("name", listResponse.get(position).getName());
                intent.putExtra("storeName", listResponse.get(position).getStoreName());
                intent.putExtra("desc", listResponse.get(position).getDescription());
                intent.putExtra("price", listResponse.get(position).getPrice());
                intent.putExtra("photo", listResponse.get(position).getPhoto());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listResponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProd;
        TextView tvProdName, tvStoreName, tvDesc, tvOriPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProd = itemView.findViewById(R.id.img_row_product);
            tvProdName = itemView.findViewById(R.id.tv_row_product);
            tvStoreName = itemView.findViewById(R.id.tv_row_store_name);
            tvDesc = itemView.findViewById(R.id.tv_row_desc);
            tvOriPrice = itemView.findViewById(R.id.tv_row_product_price);

        }
    }
}
