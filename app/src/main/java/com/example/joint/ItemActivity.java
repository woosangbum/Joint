package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemActivity extends AppCompatActivity {
    // 물품 게시글 상세글 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView tvName = (TextView)findViewById(R.id.item_name);
//        ImageView img = (ImageView)findViewById(R.id.itemImageView);
        TextView tvDeadlineDate = (TextView)findViewById(R.id.item_deadlineDate);
        TextView tvContent = (TextView)findViewById(R.id.item_content);
        TextView tvTargetNum = (TextView)findViewById(R.id.item_targetNum);
        TextView tvCurrNum = (TextView)findViewById(R.id.item_currNum);
        TextView tvPrice = (TextView)findViewById(R.id.item_price);
        TextView tvDiscountPrice = (TextView)findViewById(R.id.item_discountPrice);
        TextView tvCreationDate = (TextView)findViewById(R.id.item_creationDate);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvName.setText(intent.getStringExtra("name"));
//        img.setImageResource(Integer.parseInt(intent.getStringExtra("icon")));
        tvDeadlineDate.setText(intent.getStringExtra("deadlineDate"));
        tvContent.setText(intent.getStringExtra("content"));
        tvTargetNum.setText(intent.getStringExtra("targetNum"));
        tvCurrNum.setText(intent.getStringExtra("currNum"));
        tvPrice.setText(intent.getStringExtra("price"));
        tvDiscountPrice.setText(intent.getStringExtra("discountPrice"));
        tvCreationDate.setText(intent.getStringExtra("creationDate"));
    }

    public void onClickHome(View v){
        Intent intent = new Intent(ItemActivity.this, ItemListActivity.class);
        startActivity(intent);
    }

    public void onClickNotice(View v){
        Intent intent = new Intent(ItemActivity.this, NoticeListActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(ItemActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(ItemActivity.this, NotificationActivity.class);
        startActivity(intent);
    }
}