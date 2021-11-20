package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailsActivity extends AppCompatActivity {
    // 주문내역(구매내역)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
    }
    public void onClickNotice(View v){
        Intent intent = new Intent(OrderDetailsActivity.this, NoticeActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(OrderDetailsActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(OrderDetailsActivity.this, NotificationActivity.class);
        startActivity(intent);
    }
}