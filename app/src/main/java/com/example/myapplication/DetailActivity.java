package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Firebase 데이터베이스 레퍼런스 가져오기
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // 가져올 글의 ID (예: "pp_01")
        String postId = getIntent().getStringExtra("ID");;

        // 글의 데이터를 불러오기
        databaseReference.child("User").child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 데이터가 존재하는 경우
                    User user = dataSnapshot.getValue(User.class);

                    // detail_activity.xml에 있는 뷰들에 데이터 설정
                    TextView titleTextView = findViewById(R.id.detail_write);
                    TextView personTextView = findViewById(R.id.detail_input_person);
                    TextView whoTextView = findViewById(R.id.detail_input_who);
                    TextView detailTextView = findViewById(R.id.detail_input_detail);

                    titleTextView.setText(user.getTitle());
                    personTextView.setText(String.valueOf(user.getPerson()));
                    whoTextView.setText(user.getId());
                    detailTextView.setText(user.getDetail());
                    // 필요한 데이터를 사용하도록 로직을 추가하세요.
                } else {
                    // 데이터가 없는 경우 또는 에러 처리
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터를 불러오는 중에 에러가 발생한 경우
            }
        });

        // ToggleButton 초기화
        toggleButton = findViewById(R.id.join);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 참가 상태일 때의 처리
                    // 현재 사용자의 ID를 가져옴
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        String id = currentUser.getEmail();
                        // '@' 이후의 부분을 제거하고, 이메일 주소를 input_who TextView에 설정합니다.
                        String userEmailId = id.split("@")[0];
                        addparty(userId, userEmailId);
                    }
                    Toast.makeText(DetailActivity.this, "참가되었습니다.", Toast.LENGTH_SHORT).show();
                    // 참가 취소로 변경하려면 여기에 적절한 코드를 추가
                } else {
                    // 참가 취소 상태일 때의 처리
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        deleteparty(userId);
                    }
                        Toast.makeText(DetailActivity.this, "참가가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    // 참가로 변경하려면 여기에 적절한 코드를 추가
                }
            }
        });

    }

    public void deleteparty(String userId) {
        String postId = getIntent().getStringExtra("ID");
        DatabaseReference partyReference = databaseReference.child("Party").child(postId);
        partyReference.child(userId).removeValue();
    }

    public void addparty(String userId, String friendId) {
        // 사용자의 파티 노드에 대한 참조 생성
        DatabaseReference partyReference = databaseReference.child("Party");

        // 사용자의 파티 노드에 친구의 ID 추가
        String postId = getIntent().getStringExtra("ID");
        partyReference.child(postId).child(userId).setValue(friendId);
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