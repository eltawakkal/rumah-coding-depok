package com.example.neardeal;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    ApiEndpoint apiEndpoint;

    Toolbar toolbar;

    ImageView imgProd;
    TextView tvProdName;
    TextView tvStoreName;
    TextView tvDesc;
    TextView tvPrice;
    TextView tvPriceOri;

    String prodName, storeName, desc, price, photo, disc;
    int code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        iniView();
        getDataFromIntent();
        setItemData();
    }

    private void setItemData() {
        tvProdName.setText(prodName);
        tvStoreName.setText(storeName);
        tvDesc.setText(desc);

        if (code == 0) {
            double oriPrice = Double.parseDouble(price);
            double discPrice = Double.parseDouble(disc);
            double totalDisc = (oriPrice / 100) * discPrice;

            tvPriceOri.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvPriceOri.setText("Rp. " + price);
            tvPrice.setText("Rp. " + Math.round(totalDisc));
        } else {
            tvPriceOri.setVisibility(View.GONE);
            tvPrice.setText("Rp. " + price);
        }


        Picasso.get().load(photo).into(imgProd);
    }

    private void getDataFromIntent() {

        code = getIntent().getIntExtra("code", 0);

        if (code == 0) {
            disc = getIntent().getStringExtra("disc");
            price = getIntent().getStringExtra("price");
        } else {
            price = getIntent().getStringExtra("price");
        }

        prodName = getIntent().getStringExtra("name");
        storeName = getIntent().getStringExtra("storeName");
        desc = getIntent().getStringExtra("desc");
        photo = getIntent().getStringExtra("photo");
    }

    private void iniView() {
//        init Obj
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

//        init views
        toolbar = findViewById(R.id.toolbar_detail_prod);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgProd = findViewById(R.id.img_detail_prod);
        tvProdName = findViewById(R.id.tv_detail_product_name);
        tvStoreName = findViewById(R.id.tv_detail_product_store_name);
        tvDesc = findViewById(R.id.tv_detail_product_desc);
        tvPrice = findViewById(R.id.tv_detail_product_price);
        tvPriceOri = findViewById(R.id.tv_detail_price_ori);
    }
}
