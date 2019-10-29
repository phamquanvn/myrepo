package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.hauishop.Entity.KhachHangEntity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng haui1 = new LatLng(21.055732, 105.737115);
        LatLng haui2 = new LatLng(21.077278, 105.732222);
        mMap.addMarker(new MarkerOptions().position(haui1).title("HauiShop").snippet("Công ty TNHH HauiShop Cs1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(haui1, 14));
        mMap.addMarker(new MarkerOptions().position(haui2).title("HauiShop").snippet("Công ty TNHH HauiShop Cs2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(haui2, 14));
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
