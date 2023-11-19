package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private boolean isToggleChecked = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String postId = getIntent().getStringExtra("ID");

        databaseReference.child("User").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);

                    TextView titleTextView = findViewById(R.id.detail_write);
                    TextView personTextView = findViewById(R.id.detail_input_person);
                    TextView whoTextView = findViewById(R.id.detail_input_who);
                    TextView detailTextView = findViewById(R.id.detail_input_detail);

                    titleTextView.setText(user.getTitle());
                    personTextView.setText(String.valueOf(user.getPerson()));
                    whoTextView.setText(user.getId());
                    detailTextView.setText(user.getDetail());
                } else {
                    // 데이터가 없는 경우 또는 에러 처리
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 데이터를 불러오는 중에 에러가 발생한 경우
            }
        });

        sharedPreferences = getPreferences(MODE_PRIVATE);

        toggleButton = findViewById(R.id.join);
        isToggleChecked = sharedPreferences.getBoolean("toggle_state", false);
        toggleButton.setChecked(isToggleChecked);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("toggle_state", isChecked);
                editor.apply();

                if (isChecked) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        String id = currentUser.getEmail();
                        String userEmailId = id.split("@")[0];
                        addparty(userId, userEmailId);
                    }
                    Toast.makeText(DetailActivity.this, "참가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        deleteparty(userId);
                        deleteUser(postId); // 글 삭제 메서드 호출
                    }
                    Toast.makeText(DetailActivity.this, "참가가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 수정 버튼에 대한 처리
        Button editButton = findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("ID", postId);
                startActivity(intent);
            }
        });

        // 삭제 버튼에 대한 처리
        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    deleteparty(userId);
                    deleteUser(postId); // 글 삭제 메서드 호출
                    Toast.makeText(DetailActivity.this, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    finish(); // 현재 엑티비티 종료
                }
            }
        });
    }

    private void deleteUser(String postId) {
        DatabaseReference userReference = databaseReference.child("User").child(postId);
        userReference.removeValue();
    }

    public void deleteparty(String userId) {
        String postId = getIntent().getStringExtra("ID");
        DatabaseReference partyReference = databaseReference.child("Party").child(postId);
        partyReference.child(userId).removeValue();
    }

    public void addparty(String userId, String friendId) {
        DatabaseReference partyReference = databaseReference.child("Party");
        String postId = getIntent().getStringExtra("ID");
        partyReference.child(postId).child(userId).setValue(friendId);
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