package com.example.joint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener{
    //파이어베이스 데이터베이스 연동
//    private DatabaseReference ref  = FirebaseDatabase.getInstance().getReference();

    //define view objects
    EditText editTextName; // 이름
    EditText editTextEmail; // 이메일
    EditText editTextPassword; // 비밀번호
    EditText editTextStudentId; // 학번
    EditText editTextPhoneNumber; //핸드폰 번호

    Button buttonSignup;
    TextView textViewLogIn;
    ProgressDialog progressDialog;

    //define firebase object
    FirebaseAuth firebaseAuth;


    // 회원가입
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 ItemList 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), ItemListActivity.class)); //추가해 줄 ProfileActivity
        }

        //initializing views
        editTextName = (EditText) findViewById(R.id.editTextPersonName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumer);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        textViewLogIn = (TextView) findViewById(R.id.textViewLogIn);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignup.setOnClickListener((View.OnClickListener) this);
        textViewLogIn.setOnClickListener((View.OnClickListener) this);
    }

    //Firebase creating a new user
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String studentId = editTextStudentId.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "이름를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(studentId)){
            Toast.makeText(this, "학번을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "핸드폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
//                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        User user = new User(name, phoneNumber, email, studentId);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("user");
                        reference.child(studentId).setValue(user);

                        Toast.makeText(JoinActivity.this, "등록 성공", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
                    } else {
                        //에러발생시
                        Toast.makeText(JoinActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });

    }

    //button click event
    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            registerUser(); //추가해 줄 로그인 액티비티
        }
        if(view == textViewLogIn){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}