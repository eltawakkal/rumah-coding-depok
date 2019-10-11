package com.example.neardeal.network;

import com.example.neardeal.response.DealResponse;
import com.example.neardeal.response.ProductResponse;
import com.example.neardeal.response.StoreResponse;
import com.example.neardeal.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiEndpoint {

    @GET("get_store.php")
    Call<List<StoreResponse>> getStore();

    @GET("get_deal.php")
    Call<List<DealResponse>> getDeal();

    @GET("get_product.php")
    Call<List<ProductResponse>> getProduct();

    @POST("get_spec_store.php")
    @FormUrlEncoded
    Call<StoreResponse> getSpecStore(@Field("idStore") String idStore);

    @POST("user_login.php")
    @FormUrlEncoded
    Call<UserResponse> cekUser(
            @Field("user") String user,
            @Field("pass") String pass);

    @POST("add_user.php")
    @FormUrlEncoded
    Call<UserResponse> addUser(
            @Field("user") String user,
            @Field("pass") String pass);

}