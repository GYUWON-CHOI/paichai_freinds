package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListActivity extends AppCompatActivity {
    //하단 메뉴 선언 과 Fragment 선언
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = findViewById(R.id.bottomNavi_main);
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

        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();

        // 가상의 채팅방 데이터 생성
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms.add(new ChatRoom("1", "채팅방 1"));
        chatRooms.add(new ChatRoom("2", "채팅방 2"));

        // 어댑터 생성
        final ChatRoomAdapter adapter = new ChatRoomAdapter(this, R.layout.list_item_chat_room, chatRooms);

        // 리스트뷰에 어댑터 설정
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // 리스트뷰 아이템 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭된 채팅방 정보 가져오기
                ChatRoom selectedChatRoom = adapter.getItem(position);

                // PartyChatActivity로 이동하는 인텐트 생성
                Intent intent = new Intent(ChatRoomListActivity.this, PartyChatActivity.class);
                intent.putExtra("roomId", selectedChatRoom.getRoomId());
                intent.putExtra("roomName", selectedChatRoom.getRoomName());

                // PartyChatActivity 시작
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
