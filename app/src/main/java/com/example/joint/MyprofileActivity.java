package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MyprofileActivity extends AppCompatActivity {
    // 내정보
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
    }
    public void onClickNotice(View v){
        Intent intent = new Intent(MyprofileActivity.this, NoticeActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(MyprofileActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(MyprofileActivity.this, NotificationActivity.class);
        startActivity(intent);
    }
}