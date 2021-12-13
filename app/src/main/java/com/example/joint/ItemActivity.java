package com.example.joint;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemActivity extends AppCompatActivity {

    // 물품 게시글 상세글 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView tvName = (TextView)findViewById(R.id.item_name);
        ImageView img = (ImageView)findViewById(R.id.item_image);
        TextView tvDeadlineDate = (TextView)findViewById(R.id.item_deadlineDate);
        TextView tvContent = (TextView)findViewById(R.id.item_content);
        TextView tvTargetNum = (TextView)findViewById(R.id.item_targetNum);
        TextView tvCurrNum = (TextView)findViewById(R.id.item_currNum);
        TextView tvPrice = (TextView)findViewById(R.id.item_price);
        TextView tvDiscountPrice = (TextView)findViewById(R.id.item_discountPrice);
        TextView tvCreationDate = (TextView)findViewById(R.id.item_creationDate);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvName.setText(intent.getStringExtra("name"));
        tvDeadlineDate.setText(intent.getStringExtra("deadlineDate"));
        tvContent.setText(intent.getStringExtra("content"));
        tvTargetNum.setText(intent.getStringExtra("targetNum"));
        tvCurrNum.setText(intent.getStringExtra("currNum"));
        tvPrice.setText(intent.getStringExtra("price"));
        tvDiscountPrice.setText(intent.getStringExtra("discountPrice"));
        tvCreationDate.setText(intent.getStringExtra("creationDate"));

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child(intent.getStringExtra("icon")).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .into(img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
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