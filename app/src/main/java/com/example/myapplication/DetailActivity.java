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
    private User user;

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
                    user = dataSnapshot.getValue(User.class);

                    TextView titleTextView = findViewById(R.id.detail_write);
                    TextView personTextView = findViewById(R.id.detail_input_person);
                    TextView whoTextView = findViewById(R.id.detail_input_who);
                    TextView detailTextView = findViewById(R.id.detail_input_detail);

                    titleTextView.setText(user.getTitle());
                    personTextView.setText(String.valueOf(user.getPerson()));
                    whoTextView.setText(user.getId());
                    detailTextView.setText(user.getDetail());

                    // 수정 및 삭제 버튼 처리
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String loggedInUserId = currentUser.getUid();
                        if (loggedInUserId.equals(user.getId())) {
                            // 현재 로그인한 사용자가 방장인 경우에만 수정 및 삭제 버튼 활성화
                            Button editButton = findViewById(R.id.edit);
                            Button deleteButton = findViewById(R.id.delete);

                            editButton.setVisibility(View.VISIBLE);
                            deleteButton.setVisibility(View.VISIBLE);
                        }
                    }
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
                handleToggleChange(isChecked);
            }
        });

        // 수정 버튼에 대한 처리
        Button editButton = findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // 글 작성자 아이디
                    String authorId = user != null ? user.getId() : "";

                    // 로그인한 유저 아이디 (이메일 형식으로 가져오기)
                    String loggedInUserId = currentUser.getEmail() != null ? currentUser.getEmail().split("@")[0] : "";

                    if (!authorId.isEmpty() && authorId.equals(loggedInUserId)) {
                        // 현재 로그인한 사용자가 글 작성자인 경우에만 수정 가능
                        Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                        intent.putExtra("ID", postId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DetailActivity.this, "글을 수정할 수 있는 권한이 없습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // 삭제 버튼에 대한 처리
        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // 방장 아이디
                    String ownerId = user != null ? user.getId() : "";

                    // 로그인한 유저 아이디 (이메일 형식으로 가져오기)
                    String loggedInUserId = currentUser.getEmail() != null ? currentUser.getEmail().split("@")[0] : "";

                    if (!ownerId.isEmpty() && ownerId.equals(loggedInUserId)) {
                        // 현재 로그인한 사용자가 방장인 경우에만 삭제 가능
                        deleteparty(loggedInUserId);
                        deleteUser(postId); // 글 삭제 메서드 호출
                        String toastMessage = "글이 삭제되었습니다.";
                        Toast.makeText(DetailActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                        finish(); // 현재 엑티비티 종료
                    } else {
                        Toast.makeText(DetailActivity.this, "글을 삭제할 수 있는 권한이 없습니다.", Toast.LENGTH_LONG).show();
                    }
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

    // ToggleButton의 OnCheckedChangeListener 내부에서 호출되는 메서드
    private void handleToggleChange(boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("toggle_state", isChecked);
        editor.apply();

        if (isChecked) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();  // Firebase 사용자의 UID를 가져옴
                String email = currentUser.getEmail();  // Firebase 사용자의 이메일 주소를 가져옴

                // 이메일 주소에서 "@" 이후의 부분을 제외하고 사용자 아이디로 사용
                String userEmailId = email != null ? email.split("@")[0] : "";

                // 여기서 isUserInParty 메서드 호출
                isUserInParty(userId, userEmailId);
            }
        } else {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
            }
            Toast.makeText(DetailActivity.this, "참가가 취소되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // isUserInParty 메서드
    private void isUserInParty(String userId, String userEmailId) {
        String postId = getIntent().getStringExtra("ID");
        DatabaseReference partyReference = databaseReference.child("Party").child(postId);

        // 해당 사용자의 파티 참가 여부 확인
        partyReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isInParty = dataSnapshot.exists();
                if (!isInParty) {
                    Toast.makeText(DetailActivity.this, "파티에 참가해야 채팅이 가능합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 채팅 가능한 경우에는 채팅 화면으로 이동
                    enterChatRoom();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
            }
        });
    }

    // 채팅 화면으로 이동하는 메서드
    private void enterChatRoom() {
        Intent intent = new Intent(DetailActivity.this, PartyChatActivity.class);
        // 필요에 따라 데이터를 전달하려면 아래 주석을 참고하세요.
        // intent.putExtra("key", value);
        startActivity(intent);
    }
}
