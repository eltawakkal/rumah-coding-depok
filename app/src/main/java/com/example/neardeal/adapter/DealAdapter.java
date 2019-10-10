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
import com.example.neardeal.response.StoreResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private Context context;
    private List<DealResponse> listResponse;

    public DealAdapter(Context context, List<DealResponse> listResponse) {
        this.context = context;
        this.listResponse = listResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_deal, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProdName.setText(listResponse.get(position).getProductName());
        holder.tvStoreName.setText(listResponse.get(position).getStoreName());
        holder.tvOriPrice.setText("Rp. " + listResponse.get(position).getPrice());
        holder.tvOriPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        double price = Double.parseDouble(listResponse.get(position).getPrice());
        double disc = Double.parseDouble(listResponse.get(position).getDiscount());
        double totalDisc = (price/100) * disc;

        holder.tvDiscPrie.setText("Rp. " + Math.round(totalDisc));

        Picasso.get().load(listResponse.get(position).getPhoto()).into(holder.imgProd);

        holder.imgProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("name", listResponse.get(position).getProductName());
                intent.putExtra("storeName", listResponse.get(position).getStoreName());
                intent.putExtra("desc", listResponse.get(position).getDescripion());
                intent.putExtra("price", listResponse.get(position).getPrice());
                intent.putExtra("photo", listResponse.get(position).getPhoto());
                intent.putExtra("disc", listResponse.get(position).getDiscount());
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
        TextView tvProdName, tvStoreName, tvOriPrice, tvDiscPrie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProd = itemView.findViewById(R.id.img_deal_prod);
            tvProdName = itemView.findViewById(R.id.prod_name);
            tvStoreName = itemView.findViewById(R.id.store_name);
            tvOriPrice = itemView.findViewById(R.id.ori_price);
            tvDiscPrie = itemView.findViewById(R.id.disc_price);

        }
    }
}
