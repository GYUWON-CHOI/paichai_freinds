package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    private EditText editTitle, editPerson, editWho, editDetail;
    private Button btnSave, btnCancel;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = findViewById(R.id.input_write);
        editPerson = findViewById(R.id.input_person);
        editWho = findViewById(R.id.input_who);
        editDetail = findViewById(R.id.input_detail);
        btnSave = findViewById(R.id.btn);
        btnCancel = findViewById(R.id.can);

        // 수정할 글의 ID를 받아옴
        String postId = getIntent().getStringExtra("ID");

        // Firebase 데이터베이스의 "User" 노드에서 해당 글의 데이터를 불러옴
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(postId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User 클래스의 생성자를 이용하여 데이터 가져오기
                    User user = dataSnapshot.getValue(User.class);

                    // 불러온 데이터를 EditText에 설정
                    editTitle.setText(user.getTitle());
                    editPerson.setText(String.valueOf(user.getPerson()));
                    editWho.setText(user.getId());
                    editDetail.setText(user.getDetail());
                } else {
                    // 데이터가 없는 경우 또는 에러 처리
                    Toast.makeText(EditActivity.this, "데이터를 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터를 불러오는 중에 에러가 발생한 경우
                Toast.makeText(EditActivity.this, "데이터를 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // 저장 버튼 클릭 시 수정된 데이터를 Firebase에 업데이트
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(postId);
            }
        });

        // 취소 버튼 클릭 시 현재 화면 닫음
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 수정된 데이터를 Firebase에 업데이트하는 메서드
    private void saveChanges(String postId) {
        String title = editTitle.getText().toString().trim();
        String personStr = editPerson.getText().toString().trim();
        String who = editWho.getText().toString().trim();
        String detail = editDetail.getText().toString().trim();

        if (title.isEmpty() || personStr.isEmpty() || who.isEmpty() || detail.isEmpty()) {
            Toast.makeText(this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int person = Integer.parseInt(personStr);

        // 수정된 데이터를 User 객체에 설정
        User updatedUser = new User(who, person, title, detail);

        // Firebase에 업데이트
        databaseReference.setValue(updatedUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(EditActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "수정 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
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