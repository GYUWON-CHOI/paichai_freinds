package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd; // 로그인 입력 필드
    private CheckBox mCheckboxAutoLogin; // 자동 로그인 체크박스
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Firebaselog");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mCheckboxAutoLogin = findViewById(R.id.checkbox_auto_login);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            if (mCheckboxAutoLogin.isChecked()) {
                                // 사용자가 자동 로그인을 선택한 경우, 설정을 저장
                                saveAutoLoginSetting(true);
                            }
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 자동 로그인 시도
        tryAutoLogin();
    }

    private void tryAutoLogin() {
        // 자동 로그인 여부를 불러와서 확인
        boolean autoLoginEnabled = getAutoLoginSetting();
        if (autoLoginEnabled) {
            FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
            if (currentUser != null) {
                // 이미 로그인된 사용자가 있다면 메인 화면으로 이동
                Log.d("AutoLogin", "User is already signed in");
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                // 자동 로그인 실패 시 처리 (현재는 아무것도 하지 않음)
                Log.d("AutoLogin", "No user is signed in");
            }
        }
    }

    private void saveAutoLoginSetting(boolean autoLogin) {
        // SharedPreferences를 사용하여 자동 로그인 설정을 저장
        sharedPreferences = getSharedPreferences("AutoLoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("autoLogin", autoLogin);
        editor.apply();
    }

    private boolean getAutoLoginSetting() {
        // SharedPreferences에서 자동 로그인 설정을 불러옴
        sharedPreferences = getSharedPreferences("AutoLoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("autoLogin", false);
    }
}