package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeListActivity extends AppCompatActivity {
    // 공시사항 리스트
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
    }
    public void onClickNotice(View v){
        Intent intent = new Intent(NoticeListActivity.this, NoticeActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(NoticeListActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(NoticeListActivity.this, NotificationActivity.class);
        startActivity(intent);
    }
}