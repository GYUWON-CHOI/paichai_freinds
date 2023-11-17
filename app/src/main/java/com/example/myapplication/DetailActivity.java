package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

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