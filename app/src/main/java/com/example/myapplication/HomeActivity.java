package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mFirebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // FirebaseAuth 초기화
        mFirebaseAuth = FirebaseAuth.getInstance();

        // 각 항목의 LinearLayout을 찾아옵니다.
        LinearLayout movieRankLayout = findViewById(R.id.movieRankLayout);
        LinearLayout searchRankLayout = findViewById(R.id.searchRankLayout);
        LinearLayout nearbyRestaurantLayout = findViewById(R.id.nearbyRestaurantLayout);
        LinearLayout nearbyPCBangLayout = findViewById(R.id.nearbyPCBangLayout);
        LinearLayout somethingLayout = findViewById(R.id.somethingLayout);

        // 클릭 리스너를 설정합니다.
        movieRankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 영화 순위 항목을 클릭했을 때의 동작을 정의합니다.
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });
//
        searchRankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색 순위 항목을 클릭했을 때의 동작을 정의합니다.
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        nearbyRestaurantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 주변 식당 항목을 클릭했을 때의 동작을 정의합니다.
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        nearbyPCBangLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 주변 PC방 항목을 클릭했을 때의 동작을 정의합니다.
                Intent intent = new Intent(HomeActivity.this, MovieActivity.class);
                startActivity(intent);
            }
        });

        somethingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // something 항목을 클릭했을 때의 동작을 정의합니다.
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavi_main);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    Intent chat = new Intent(getApplicationContext(), ChatRoomListActivity.class);
                    startActivity(chat);
                    break;
                case R.id.menu_home:
                    Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(home);
                    break;
                case R.id.menu_profile:
                    Intent profile = new Intent(getApplicationContext(), Frag3Activity.class);
                    startActivity(profile);
                    break;
            }
            return false;
        });
    }

    private void clearAutoLoginSetting() {
        // SharedPreferences를 사용하여 자동 로그인 설정 초기화
        getSharedPreferences("AutoLoginPrefs", MODE_PRIVATE)
                .edit()
                .remove("autoLogin")
                .apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.menu_write:
                Intent writeIntent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(writeIntent);
                break;

            case R.id.menu_logout:
                // 로그아웃 시 자동 로그인 설정 초기화
                clearAutoLoginSetting();

                if (mFirebaseAuth != null) {
                    mFirebaseAuth.signOut();
                    Toast.makeText(HomeActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                    // 로그인 화면으로 이동
                    Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish(); // 현재 액티비티 종료
                }
                break;

            case R.id.menu_movie:
                Intent movieIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(movieIntent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
