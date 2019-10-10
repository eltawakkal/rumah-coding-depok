package com.example.neardeal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neardeal.R;
import com.example.neardeal.adapter.StoreAdapter;
import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.StoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment {

    ApiEndpoint apiEndpoint;

    RecyclerView recStore;

    StoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        recStore = view.findViewById(R.id.rec_store);

        getStore();

        return view;
    }

    private void setRecStore(List<StoreResponse> listStore) {
        adapter = new StoreAdapter(getContext(), listStore);
        recStore.setLayoutManager(new LinearLayoutManager(getContext()));
        recStore.setAdapter(adapter);

    }

    private void getStore() {
        Call<List<StoreResponse>> callStore = apiEndpoint.getStore();
        callStore.enqueue(new Callback<List<StoreResponse>>() {
            @Override
            public void onResponse(Call<List<StoreResponse>> call, Response<List<StoreResponse>> response) {
                List<StoreResponse> listStoreResponse = response.body();

                setRecStore(listStoreResponse);
            }

            @Override
            public void onFailure(Call<List<StoreResponse>> call, Throwable t) {
                Log.e("gagal", t.getMessage());
            }
        });
    }
}
