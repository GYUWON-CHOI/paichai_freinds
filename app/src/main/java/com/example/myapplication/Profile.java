package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chang_nick_message);
        bottomNavigationView = findViewById(R.id.bottomNavi_main_info);
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
                case R.id.menu_profile:
                    Intent profile = new Intent(getApplicationContext(), Frag3Activity.class);
                    startActivity(profile);
                    break;

            }
            return false;
        });

    }


}
