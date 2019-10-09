package com.example.neardeal;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.neardeal.network.ApiClient;
import com.example.neardeal.network.ApiEndpoint;
import com.example.neardeal.response.StoreResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapStore;
    GoogleMap mGoogleMap;

    ApiEndpoint apiEndpoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapStore = findViewById(R.id.map_store);
        apiEndpoint = ApiClient.getRetrofit().create(ApiEndpoint.class);

        if (mapStore != null) {
            mapStore.onCreate(null);
            mapStore.onResume();
            mapStore.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        getStoreData();
    }

    void setMapMarker(LatLng coordinate, String storeName) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.store);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(coordinate)
                .title(storeName)
                .snippet("Sedang Buka")
                .icon(icon);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(coordinate).zoom(10).build();

        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    void setPolyline(List<LatLng> points) {
        //add simple poly line
        PolylineOptions polyLineOptions = new PolylineOptions().width(5).color(R.color.colorPrimary).geodesic(true);
        polyLineOptions.clickable(true);

        polyLineOptions.addAll(points);
        mGoogleMap.addPolyline(polyLineOptions);
    }

    void getStoreData() {
        Call<List<StoreResponse>> callStore = apiEndpoint.getStore();
        callStore.enqueue(new Callback<List<StoreResponse>>() {
            @Override
            public void onResponse(Call<List<StoreResponse>> call, Response<List<StoreResponse>> response) {
                List<StoreResponse> listStore = response.body();

                List<LatLng> points = new ArrayList<>();

                for (int i =0; i < listStore.size(); i ++) {

                    points.add(new
                            LatLng(Double.parseDouble(listStore.get(i).getLat()),
                            Double.parseDouble(listStore.get(i).getLng())));

                    LatLng coordinate = new LatLng(
                            Double.parseDouble(listStore.get(i).getLat()),
                            Double.parseDouble(listStore.get(i).getLng()));

                    setMapMarker(coordinate, listStore.get(i).getName());
                }

                setPolyline(points);
            }

            @Override
            public void onFailure(Call<List<StoreResponse>> call, Throwable t) {

            }
        });
    }
}
