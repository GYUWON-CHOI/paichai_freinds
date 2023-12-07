package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.FragmentFoodActivityBinding;

public class FoodActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentFoodActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentFoodActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we add a marker for Baejae University and move the camera.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng baejaeUniversity = new LatLng(36.322523, 127.370224);
        LatLng pic = new LatLng(36.317358, 127.368361);
        LatLng nayeong = new LatLng(36.317487, 127.368341);
        LatLng pork = new LatLng(36.317189, 127.368676);
        LatLng nice = new LatLng(36.317139, 127.368786);
        LatLng kimbab = new LatLng(36.317177, 127.368715);
        LatLng cheong = new LatLng(36.317115, 127.368938);
        LatLng mf = new LatLng(36.317072, 127.369098);
        LatLng km = new LatLng(36.317031, 127.369335);
        LatLng en = new LatLng(36.316970, 127.369572);

        // Add a marker at Baejae University and move the camera
        mMap.addMarker(new MarkerOptions().position(pic).title("그림같은집"));
        mMap.addMarker(new MarkerOptions().position(nayeong).title("나영왕돈까스"));
        mMap.addMarker(new MarkerOptions().position(pork).title("아줌마네식당"));
        mMap.addMarker(new MarkerOptions().position(nice).title("떡볶이 참 잘하는집"));
        mMap.addMarker(new MarkerOptions().position(kimbab).title("바로김밥"));
        mMap.addMarker(new MarkerOptions().position(cheong).title("청년식당"));
        mMap.addMarker(new MarkerOptions().position(mf).title("부부식당"));
        mMap.addMarker(new MarkerOptions().position(km).title("금래원"));
        mMap.addMarker(new MarkerOptions().position(en).title("은혜식당"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baejaeUniversity, 18f)); // Adjust the zoom level as needed
    }
}