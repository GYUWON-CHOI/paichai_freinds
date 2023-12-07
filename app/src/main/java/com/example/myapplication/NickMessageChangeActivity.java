// java/com.example.myapplication/NickMessageChangeActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NickMessageChangeActivity extends AppCompatActivity {
    private TextView nickTextView;
    private EditText nickEditText, statusEditText;
    private Button changeNickButton, changeStatusButton;
    private TextView currentNickTextView, currentStatusTextView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chang_nick_message);
        setContentView(R.layout.frag3);

        nickEditText = findViewById(R.id.nickEditText);
        statusEditText = findViewById(R.id.statusEditText);
        changeNickButton = findViewById(R.id.changeNickButton);
        changeStatusButton = findViewById(R.id.changeStatusButton);
        currentNickTextView = findViewById(R.id.currentNickTextView);

        // 여기에 추가된 부분
        View view = findViewById(android.R.id.content);

        currentStatusTextView = findViewById(R.id.currentStatusTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // 닉네임과 상태 메시지를 가져와서 표시
            databaseReference.child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().hasChild("nick")) {
                        String nick = task.getResult().child("nick").getValue().toString();
                        currentNickTextView.setText("현재 닉네임: " + nick);

                        // Frag3에 있는 nickname TextView에도 닉네임 설정
                        TextView frag3NickTextView = findViewById(R.id.nickname);
                        frag3NickTextView.setText("현재 닉네임:" + nick);

                        // 이 부분에서 chang_nick_message.xml에 있는 nickname TextView에도 설정
                        nickTextView.setText("현재 닉네임:" + nick); // 추가된 부분
                    }
                    if (task.getResult().hasChild("status")) {
                        String status = task.getResult().child("status").getValue().toString();
                        currentStatusTextView.setText("현재 상태 메시지: " + status);
                    }
                }
            });

            changeNickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeNick();
                }
            });

            changeStatusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatus();
                }
            });
        }
    }

    private void changeNick() {
        String newNick = nickEditText.getText().toString().trim();
        if (newNick.isEmpty()) {
            Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // 닉네임 변경
            databaseReference.child(userId).child("nick").setValue(newNick)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            currentNickTextView.setText("현재 닉네임: " + newNick);
                            nickTextView.setText("현재 닉네임: " + newNick); // 추가된 부분
                            Toast.makeText(NickMessageChangeActivity.this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NickMessageChangeActivity.this, "닉네임 변경 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void changeStatus() {
        String newStatus = statusEditText.getText().toString().trim();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // 상태 메시지 변경
            databaseReference.child(userId).child("status").setValue(newStatus)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            currentStatusTextView.setText("현재 상태 메시지: " + newStatus);
                            Toast.makeText(NickMessageChangeActivity.this, "상태 메시지가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NickMessageChangeActivity.this, "상태 메시지 변경 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
