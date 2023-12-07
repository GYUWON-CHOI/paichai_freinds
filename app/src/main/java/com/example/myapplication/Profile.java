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
        setContentView(R.layout.profile);
        bottomNavigationView = findViewById(R.id.bottomNavi_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_chat:
                    Intent chat = new Intent(getApplicationContext(), ChatRoomListActivity.class);
                    startActivity(chat);
                    break;
                case R.id.menu_home:
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
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


}
