package com.example.neardeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.preference.PrefStore;
import com.example.neardeal.response.UserResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ApiEndpoint apiEndpoint;
    PrefStore prefStore;

    TextInputEditText edtEmail, edtPass;
    MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        if (isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();

                cekUser(user, pass);
            }
        });

    }

    boolean isUserLoggedIn() {
        String user = prefStore.getUsername();

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    private void cekUser(String user, String pass) {
        Call<UserResponse> callCekUesr = apiEndpoint.cekUser(user, pass);
        callCekUesr.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                updateUI(response);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("gagal", t.getMessage());
            }
        });
    }

    private void updateUI(Response<UserResponse> response) {
        UserResponse userResponse = response.body();

        if (!userResponse.getUser().equals("null")) {
            startActivity(new Intent(this, MainActivity.class));

            //saev user into preference
            prefStore.setUser(userResponse.getUser(), userResponse.getPass());

            finish();
        } else {
            Toast.makeText(this, "Pengguna atau Sandi Tidak Dikenal!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);
        prefStore = new PrefStore(this);

        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btn_login);
    }
}
