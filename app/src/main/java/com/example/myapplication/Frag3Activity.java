package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import  android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Frag3Activity extends AppCompatActivity {
    private Button profile;
    private Button info;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3);
        bottomNavigationView = findViewById(R.id.bottomNavi_main);

        // 하단 네비게이션 아이템 선택 리스너 설정
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
                case R.id.menu_party:
                    Intent party = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(party);
                    break;
                case R.id.menu_profile:
                    // 이미 현재 액티비티이므로 따로 이동할 필요 없음
                    break;
            }
            return false;
        });


        //proflie로 이동
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(Frag3Activity.this, NickMessageChangeActivity.class);
            startActivity(intent); //액티비티 이동.
        });

        info = findViewById(R.id.info);
        info.setOnClickListener(view -> {
            Intent intent = new Intent(Frag3Activity.this, PasswordChangeActivity.class);
            startActivity(intent); //액티비티 이동.
        });
    }

}
