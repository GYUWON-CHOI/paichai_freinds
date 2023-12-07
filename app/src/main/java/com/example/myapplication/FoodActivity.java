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
        LatLng 맘스터치 = new LatLng(36.322466, 127.370470);
        LatLng 내가찜한닭 = new LatLng(36.322678, 127.370417);
        LatLng 본죽 = new LatLng( 36.323251, 127.370448);
        LatLng 큰맘할매순대국 = new LatLng(36.323361, 127.370465);
        LatLng 핵밥 = new LatLng(36.322011, 127.370675);
        LatLng 고봉민김밥 = new LatLng(36.322426, 127.370665);
        LatLng 로충칭마라탕 = new LatLng(36.322597, 127.370605);
        LatLng 봉구스밥버거 = new LatLng(36.324057, 127.370487);
        LatLng 롯데리아 = new LatLng( 36.324214, 127.370504);
        LatLng 해도 = new LatLng(36.322596, 127.370791);
        LatLng 부대통령뚝배기 = new LatLng(36.322717, 127.370835);
        LatLng 김밥천국 = new LatLng(36.322421, 127.371017);
        LatLng 행컵 = new LatLng(36.322573, 127.371245);
        LatLng 뜸들이다 = new LatLng(36.322423, 127.371290);
        LatLng 경남성 = new LatLng(36.322207, 127.369980);
        LatLng 프랭크버거 = new LatLng(36.323392, 127.370448);
        LatLng 응급실국물떡볶이 = new LatLng(36.323125, 127.370474);


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
        mMap.addMarker(new MarkerOptions().position(맘스터치).title("맘스터치"));
        mMap.addMarker(new MarkerOptions().position(내가찜한닭).title("내가찜한닭"));
        mMap.addMarker(new MarkerOptions().position(본죽).title("본죽"));
        mMap.addMarker(new MarkerOptions().position(큰맘할매순대국).title("큰맘할매순대국"));
        mMap.addMarker(new MarkerOptions().position(핵밥).title("핵밥"));
        mMap.addMarker(new MarkerOptions().position(고봉민김밥).title("고봉민김밥"));
        mMap.addMarker(new MarkerOptions().position(로충칭마라탕).title("로충칭마라탕"));
        mMap.addMarker(new MarkerOptions().position(봉구스밥버거).title("봉구스밥버거"));
        mMap.addMarker(new MarkerOptions().position(롯데리아).title("롯데리아"));
        mMap.addMarker(new MarkerOptions().position(해도).title("해도"));
        mMap.addMarker(new MarkerOptions().position(부대통령뚝배기).title("부대통령뚝배기"));
        mMap.addMarker(new MarkerOptions().position(김밥천국).title("김밥천국"));
        mMap.addMarker(new MarkerOptions().position(행컵).title("행컵"));
        mMap.addMarker(new MarkerOptions().position(뜸들이다).title("뜸들이다"));
        mMap.addMarker(new MarkerOptions().position(경남성).title("경남성"));
        mMap.addMarker(new MarkerOptions().position(프랭크버거).title("프랭크버거"));
        mMap.addMarker(new MarkerOptions().position(응급실국물떡볶이).title("응급실국물떡볶이"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(baejaeUniversity, 18f)); // Adjust the zoom level as needed
    }
}