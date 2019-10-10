package com.example.neardeal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neardeal.R;
import com.example.neardeal.adapter.DealAdapter;
import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.DealResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragDealProduct extends Fragment {

    RecyclerView recDeal;

    ApiEndpoint apiEndpoint;
    DealAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_deal_product, container, false);

        recDeal = view.findViewById(R.id.rec_deal_product);
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        getDealProduct();

        return view;
    }

    private void getDealProduct() {
        Call<List<DealResponse>> callDeal = apiEndpoint.getDeal();
        callDeal.enqueue(new Callback<List<DealResponse>>() {
            @Override
            public void onResponse(Call<List<DealResponse>> call, Response<List<DealResponse>> response) {
                List<DealResponse> listDeal = response.body();

                setDealItem(listDeal);
                Toast.makeText(getContext(), "Size: " + listDeal.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<DealResponse>> call, Throwable t) {

            }
        });
    }

    private void setDealItem(List<DealResponse> listDeal) {
        adapter = new DealAdapter(getContext(), listDeal);

        recDeal.setLayoutManager(new GridLayoutManager(getContext(), 2  ));
        recDeal.setAdapter(adapter);
    }

}
