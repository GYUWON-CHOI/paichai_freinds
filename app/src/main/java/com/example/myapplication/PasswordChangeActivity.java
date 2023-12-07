package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private EditText newEmailEditText;
    private Button changePasswordButton, changeEmailButton;
    private TextView currentEmailTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_password);
        bottomNavigationView = findViewById(R.id.bottomNavi_main_info);

        // 하단 네비게이션 아이템 선택 리스너 설정
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

        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        newEmailEditText = findViewById(R.id.newEmailEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changeEmailButton = findViewById(R.id.changeEmailButton);
        currentEmailTextView = findViewById(R.id.currentEmailTextView);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentEmailTextView.setText(currentUser.getEmail());
        }

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail();
            }
        });
    }

    private void changePassword() {
        String currentPassword = currentPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "비밀번호는 최소 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(PasswordChangeActivity.this, "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(PasswordChangeActivity.this, "비밀번호 변경 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(PasswordChangeActivity.this, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void changeEmail() {
        String currentPassword = currentPasswordEditText.getText().toString();
        String newEmail = newEmailEditText.getText().toString();

        if (currentPassword.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newEmail.length() < 6) {
            Toast.makeText(this, "이메일은 최소 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updateEmail(newEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    currentEmailTextView.setText(newEmail);
                                                    Toast.makeText(PasswordChangeActivity.this, "이메일이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(PasswordChangeActivity.this, "이메일 변경 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(PasswordChangeActivity.this, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
