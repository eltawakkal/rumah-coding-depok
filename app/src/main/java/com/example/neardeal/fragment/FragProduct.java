package com.example.neardeal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.neardeal.R;
import com.example.neardeal.adapter.ProductAdapter;
import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragProduct extends Fragment {

    RecyclerView recProduct;

    ApiEndpoint apiEndpoint;
    ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product, container, false);


        recProduct = view.findViewById(R.id.rec_list_product);
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        getProduct();

        return view;
    }

    private void getProduct() {
        Call<List<ProductResponse>> callProduct = apiEndpoint.getProduct();
        callProduct.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                List<ProductResponse> listProduct = response.body();

                setProduct(listProduct);
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {

            }
        });
    }

    private void setProduct(List<ProductResponse> listProduct) {
        adapter = new ProductAdapter(getContext(), listProduct);

        recProduct.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recProduct.setAdapter(adapter);
    }

}
