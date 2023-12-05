package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class PartyChatActivity extends AppCompatActivity {

    private ListView listView;
    private EditText messageEditText;
    private Button sendButton;

    private DatabaseReference databaseReference;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    private String partyId; // 파티 식별자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, R.layout.item_chat, chatMessages);
        listView.setAdapter(chatAdapter);

        partyId = "yourPartyId"; // 실제 파티 ID로 초기화해야 합니다.

        // Firebase Realtime Database에서 파티 채팅에 대한 참조 가져오기
        databaseReference = FirebaseDatabase.getInstance().getReference().child("parties").child(partyId).child("chat");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        // 새로운 메시지 감지를 위한 리스너 등록
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                chatMessages.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
                scrollToBottom(); // 아래로 스크롤
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 업데이트된 메시지 처리 (필요한 경우)
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // 삭제된 메시지 처리 (필요한 경우)
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 이동된 메시지 처리 (필요한 경우)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 오류 처리 (필요한 경우)
            }
        });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        if (!messageText.isEmpty()) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String currentUserEmail = user.getEmail();

                // "@" 이전의 이메일 부분만 가져오기
                String[] emailParts = currentUserEmail.split("@");
                String currentUserDisplayName = emailParts[0];

                // 새로운 메시지를 데이터베이스에 저장
                DatabaseReference newMessageRef = databaseReference.push();
                ChatMessage chatMessage = new ChatMessage(currentUserDisplayName, messageText);
                newMessageRef.setValue(chatMessage);
                messageEditText.getText().clear();
            }
        }
    }

    // 스크롤을 리스트의 맨 아래로 이동시키는 메소드
    private void scrollToBottom() {
        if (chatAdapter.getCount() > 0) {
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(chatAdapter.getCount() - 1);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed(); // 뒤로가기 버튼을 눌렀을 때의 동작
            return true;
        }

        // 다른 메뉴 아이템 처리...

        return super.onOptionsItemSelected(item);
    }
}

