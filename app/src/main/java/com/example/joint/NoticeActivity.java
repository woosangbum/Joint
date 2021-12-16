package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeActivity extends AppCompatActivity {
    // 공시사항글
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        TextView tvTitle = (TextView)findViewById(R.id.notice_post_title);
        TextView tvContent = (TextView)findViewById(R.id.notice_post_content);
        TextView tvDate = (TextView)findViewById(R.id.notice_post_date);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvTitle.setText(intent.getStringExtra("title"));
        tvContent.setText(intent.getStringExtra("content"));
        tvDate.setText(intent.getStringExtra("date"));

    }

//    public void onClickHome(View v){
//        Intent intent = new Intent(NoticeActivity.this, ItemListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotice(View v){
//        Intent intent = new Intent(NoticeActivity.this, NoticeListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickMyProfile(View v){
//        Intent intent = new Intent(NoticeActivity.this, MyprofileActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotification(View v){
//        Intent intent = new Intent(NoticeActivity.this, NotificationActivity.class);
//        startActivity(intent);
//    }
}