package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        input_who = findViewById(R.id.input_who);
        input_detail = findViewById(R.id.input_detail);

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

    public void adduser(String id, int person, String title, String detail){
        User user = new User(id, person, title, detail);
        databaseReference.child("User").child(id).setValue(user);
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