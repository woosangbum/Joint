package com.example.joint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignin;
    TextView textviewSignup;
    ProgressDialog progressDialog;

    //define firebase object
    FirebaseAuth firebaseAuth;

    public static Context mContext;

    // 로그인
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSignup= (TextView) findViewById(R.id.textViewSignup);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignin.setOnClickListener((View.OnClickListener) this);
        textviewSignup.setOnClickListener((View.OnClickListener) this);
    }

    //firebase userLogin method
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if(task.isSuccessful()) {
                        FirebaseUser userDB = FirebaseAuth.getInstance().getCurrentUser();;
                        String userEmail = userDB.getEmail().trim();
                        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (userEmail.equals(snapshot.child("email").getValue().toString())) {
                                        String studentId = snapshot.child("studentId").getValue().toString();
                                        PreferenceManager.setString(mContext, "studentId", studentId); // 값 저장
                                        PreferenceManager.setString(mContext, "userEmail", userEmail); // 값 저장
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });

                        finish();
                        startActivity(new Intent(getApplicationContext(), ItemListActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignin) {
            userLogin();
        }
        if(view == textviewSignup) {
            finish();
            startActivity(new Intent(getApplicationContext(), JoinActivity.class));
        }
    }
}