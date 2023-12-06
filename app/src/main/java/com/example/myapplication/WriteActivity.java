package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    EditText input_write;
    EditText input_person; // 수정된 부분
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
        input_person = findViewById(R.id.input_person); // 수정된 부분
        input_detail = findViewById(R.id.input_detail);
        input_who = findViewById(R.id.input_who);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            String userEmailId = userEmail.split("@")[0];
            input_who.setText(userEmailId);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정된 부분: 인원 입력값이 비어있는지 확인
                if (input_person.getText().toString().isEmpty()) {
                    Toast.makeText(WriteActivity.this, "인원을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    adduser(
                            input_who.getText().toString(),
                            Integer.parseInt(input_person.getText().toString()), // 수정된 부분
                            input_write.getText().toString(),
                            input_detail.getText().toString()
                    );
                    finish();
                }
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void adduser(String id, int person, String title, String detail) {
        User user = new User(id, person, title, detail);
        databaseReference.child("User").child(id).setValue(user);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            addparty(userId, id);
        }
    }

    public void addparty(String userId, String friendId) {
        DatabaseReference partyReference = databaseReference.child("Party");
        partyReference.child(friendId).child(userId).setValue(friendId);
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