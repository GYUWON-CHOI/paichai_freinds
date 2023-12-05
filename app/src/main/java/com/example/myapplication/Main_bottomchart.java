package com.example.myapplication;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.myapplication.Frag1;
import com.example.myapplication.Frag2;
import com.example.myapplication.Frag3;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Main_bottomchart extends AppCompatActivity {
    //하단 메뉴 선언 과 fragment 이동
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi_main);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    setFrag(0);
                    break;
                case R.id.menu_home:
                    setFrag(1);
                    break;
                case R.id.menu_profile:
                    setFrag(2);
                    break;
            }
            return false;
        });

        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
    }

    //    바텀화면 프레그먼트 교체 실행
    private void setFrag(int n) {
        try {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            switch (n) {
                case 0:
                    ft.replace(R.id.main_frame, frag1);
                    ft.commit();
                    break;
                case 1:
                    ft.replace(R.id.main_frame, frag2);
                    ft.commit();
                    break;
                case 2:
                    ft.replace(R.id.main_frame, frag3);
                    ft.commit();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "오류 발생: " + e.getMessage());
        }
    }
}

