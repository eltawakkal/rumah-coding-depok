package com.example.neardeal.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.neardeal.R;
import com.example.neardeal.adapter.StoreAdapter;
import com.example.neardeal.model.RealmStore;
import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.StoreResponse;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment {

    ApiEndpoint apiEndpoint;

    RealmConfiguration realmConfiguration;
    Realm realm;

    RecyclerView recStore;
    SwipeRefreshLayout swipeStore;

    StoreAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);

        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        recStore = view.findViewById(R.id.rec_store);
        swipeStore = view.findViewById(R.id.srl_store);


        swipeStore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStoreFromServer();
            }
        });

        RealmResults<RealmStore> storeFromLocal = realm.where(RealmStore.class).findAll();
        List<StoreResponse> listStore = new ArrayList<>();

        for (RealmStore store : storeFromLocal) {

            StoreResponse storeResponse = new StoreResponse();

            storeResponse.setName(store.getStoreName());
            storeResponse.setDescription(store.getStoreDesc());

            listStore.add(storeResponse);
        }

        setRecStore(listStore);

        getStoreFromServer();

        return view;
    }

    private void setRecStore(List<StoreResponse> listStore) {
        adapter = new StoreAdapter(getContext(), listStore);
        recStore.setLayoutManager(new LinearLayoutManager(getContext()));
        recStore.setAdapter(adapter);

    }

    private void getStoreFromServer() {
        Call<List<StoreResponse>> callStore = apiEndpoint.getStore();
        callStore.enqueue(new Callback<List<StoreResponse>>() {
            @Override
            public void onResponse(Call<List<StoreResponse>> call, Response<List<StoreResponse>> response) {
                final List<StoreResponse> listStoreResponse = response.body();

                //saving data into locale database
                saveDataIntoLocale(listStoreResponse);

                setRecStore(listStoreResponse);

                swipeStore.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<StoreResponse>> call, Throwable t) {
                Log.e("gagal", t.getMessage());

                swipeStore.setRefreshing(false);
            }
        });
    }

    private void saveDataIntoLocale(final List<StoreResponse> listStoreResponse) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (realm != null) {
                    realm.deleteAll();

                    for (StoreResponse storeResponse : listStoreResponse) {
                        RealmStore realmStore = realm.createObject(RealmStore.class);
                        realmStore.setStoreName(storeResponse.getName());
                        realmStore.setStoreDesc(storeResponse.getDescription());
                    }

                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<RealmStore> realmStores = realm.where(RealmStore.class).findAll();
                Toast.makeText(getContext(), "Size: " + realmStores.size(), Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });

    }

}
