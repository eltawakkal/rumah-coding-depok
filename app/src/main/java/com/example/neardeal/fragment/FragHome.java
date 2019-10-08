package com.example.neardeal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neardeal.R;
import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.StoreResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment {

    ApiEndpoint apiEndpoint;
    ImageView imgTest;
    RecyclerView recStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        imgTest = view.findViewById(R.id.img_test);
        recStore = view.findViewById(R.id.rec_store);

        getStore();

        return view;
    }

    private void getStore() {
        Call<List<StoreResponse>> callStore = apiEndpoint.getStore();
        callStore.enqueue(new Callback<List<StoreResponse>>() {
            @Override
            public void onResponse(Call<List<StoreResponse>> call, Response<List<StoreResponse>> response) {
                List<StoreResponse> listStoreResponse = response.body();

                StoreResponse response1 = listStoreResponse.get(1);

                Toast.makeText(getContext(), "Nama Toko: " + response1.getName(), Toast.LENGTH_SHORT).show();

                Picasso.get()
                        .load("https://github.com/square/picasso/raw/master/website/staticdadsd/sample.png")
                        .into(imgTest);
            }

            @Override
            public void onFailure(Call<List<StoreResponse>> call, Throwable t) {
                Log.e("gagal", t.getMessage());
            }
        });
    }
}
