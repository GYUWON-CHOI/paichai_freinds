package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class WriteActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    EditText input_write;
    EditText input_perseon;
    EditText input_who;
    EditText input_detail;
    Button btn, can;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        can = findViewById(R.id.can);
        btn = findViewById(R.id.btn);
        input_write = findViewById(R.id.input_write);
        input_perseon = findViewById(R.id.input_person);
        input_detail = findViewById(R.id.input_detail);
        input_who = findViewById(R.id.input_who); // 추가된 부분

        // 현재 로그인한 사용자의 정보를 가져옵니다.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // 사용자가 로그인 중인지 확인합니다.
        if (currentUser != null) {
            // 사용자의 전체 이메일 주소를 가져옵니다.
            String userEmail = currentUser.getEmail();

            // '@' 이후의 부분을 제거하고, 이메일 주소를 input_who TextView에 설정합니다.
            String userEmailId = userEmail.split("@")[0];
            input_who.setText(userEmailId); // 수정된 부분
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adduser(input_who.getText().toString(), Integer.parseInt(input_perseon.getText().toString()), input_write.getText().toString(), input_detail.getText().toString());
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                startActivity(intent);

                // 토스트 메시지 출력
                Toast.makeText(WriteActivity.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void adduser(String id, int person, String title, String detail) {
        User user = new User(id, person, title, detail);
        databaseReference.child("User").child(id).setValue(user);

        // 현재 사용자의 ID를 가져옴
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            addparty(userId, id);
        }
    }

    public void addparty(String userId, String friendId) {
        // 사용자의 파티 노드에 대한 참조 생성
        DatabaseReference partyReference = databaseReference.child("Party");

        // 사용자의 파티 노드에 친구의 ID 추가
        partyReference.child(friendId).child(userId).setValue(friendId);
    }

    // 뒤로가기 버튼을 눌렀을 때의 동작 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish(); // 현재 엑티비티 종료
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}