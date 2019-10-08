package com.example.neardeal.network;

import com.example.neardeal.response.StoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {

    @GET("get_store.php")
    Call<List<StoreResponse>> getStore();

}