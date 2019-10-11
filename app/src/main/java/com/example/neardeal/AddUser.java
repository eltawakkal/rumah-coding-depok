package com.example.neardeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.UserResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    ApiEndpoint apiEndpoint;

    EditText edtUser, edtPass;
    MaterialButton mbtDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtUser = findViewById(R.id.edt_add_user);
        edtPass = findViewById(R.id.edt_add_pass);
        mbtDaftar = findViewById(R.id.mbt_daftar);

        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        mbtDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                addNewUserToServer(user, pass);

            }
        });
    }

    private void addNewUserToServer(String user, String pass) {
        Call<UserResponse> callAddUser = apiEndpoint.addUser(user, pass);
        callAddUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();

                Toast.makeText(AddUser.this, userResponse.getUser(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(AddUser.this, "Masalah Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
