package com.example.neardeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.StoreResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStore extends AppCompatActivity  implements OnMapReadyCallback {

    GoogleMap mGoogleMap;

    ApiEndpoint apiEndpoint;
    StoreResponse storeResponse;

    MapView mapStore;
    ImageView imgStore;
    TextView tvStoreName;
    TextView tvStoreTime;
    TextView tvStoreAddress;
    TextView tvStoreDesc;

    String idStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);

         idStore = getIntent().getStringExtra("id");

        initView();
        getStore(idStore);

    }

    private void getStore(String idStore) {
        Call<StoreResponse> callSpecStore = apiEndpoint.getSpecStore(idStore);
        callSpecStore.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                storeResponse = response.body();

//                Toast.makeText(DetailStore.this, "response: " + storeResponse.getName(), Toast.LENGTH_SHORT).show();

                    setItemData(storeResponse);



                    if (storeResponse.getLat() != null) {
                        //set coordinate to mapView
                        double lat = Double.parseDouble(storeResponse.getLat());
                        double lng = Double.parseDouble(storeResponse.getLng());

                        LatLng coordinate = new LatLng(lat, lng);

                        setMapMarker(coordinate, storeResponse.getName());
                    } else {
                        //custom error or set default coordinate
                    }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {

            }
        });
    }

    private void setItemData(StoreResponse storeResponse) {
        Picasso.get().load(storeResponse.getPhoto()).into(imgStore);

        tvStoreName.setText(storeResponse.getName());
        tvStoreTime.setText(storeResponse.getOpenHour());
        tvStoreAddress.setText(storeResponse.getAddress());
        tvStoreDesc.setText(storeResponse.getDescription());
    }

    private void initView() {
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        imgStore = findViewById(R.id.img_detail_store);
        tvStoreName = findViewById(R.id.tv_detail_store_name);
        tvStoreTime = findViewById(R.id.tv_detail_store_time);
        tvStoreAddress = findViewById(R.id.tv_detail_store_address);
        tvStoreDesc = findViewById(R.id.tv_detail_store_desc);

        //setup map
        mapStore = findViewById(R.id.map_detail_store);

        if (mapStore != null) {
            mapStore.onCreate(null);
            mapStore.onResume();
            mapStore.getMapAsync(this);
        }
    }

    void setMapMarker(LatLng coordinate, String storeName) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.store);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(coordinate)
                .title(storeName)
                .snippet("Sedang Buka")
                .icon(icon);

        CameraPosition cameraPosition = new CameraPosition
                .Builder()
                .target(coordinate)
                .zoom(16)
                .build();

        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
