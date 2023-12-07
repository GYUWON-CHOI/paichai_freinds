package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.myapplication.databinding.FragmentPcActivityBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.FragmentFoodActivityBinding;

public class PcActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentPcActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPcActivityBinding.inflate(getLayoutInflater());
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

        // Baejae University LatLng
        LatLng baejaeUniversity = new LatLng(36.322523, 127.370224);
        LatLng pc533 = new LatLng(36.322327, 127.370391);
        LatLng pop3 = new LatLng(36.322175, 127.370385);

        // Add a marker at Baejae University and move the camera
        mMap.addMarker(new MarkerOptions().position(pc533).title("533PC"));
        mMap.addMarker(new MarkerOptions().position(pop3).title("3POP"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baejaeUniversity, 18f)); // Adjust the zoom level as needed
    }
}