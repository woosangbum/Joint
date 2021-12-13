package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPurchaseHistoryActivity extends AppCompatActivity {
    private ListView userPurchaseListView;
    ItemListViewAdapter adapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_purchase_history_list);

        Intent intent = getIntent();
        studentId = intent.getStringExtra("student_id");

        showPurchaseList();
    }
    public void onClickHome(View v){
        Intent intent = new Intent(UserPurchaseHistoryActivity.this, ItemListActivity.class);
        startActivity(intent);
    }

    public void onClickNotice(View v){
        Intent intent = new Intent(UserPurchaseHistoryActivity.this, NoticeListActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(UserPurchaseHistoryActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(UserPurchaseHistoryActivity.this, NotificationActivity.class);
        startActivity(intent);
    }

    private void showPurchaseList() {
        adapter = new ItemListViewAdapter();
        userPurchaseListView.setAdapter(adapter);

//        FirebaseDatabase.getInstance().getReference().child("item_list").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    if (userEmail.equals(snapshot.child("email").getValue().toString())) {
//                        textViewProfilePhoneNumber.setText(snapshot.child("phoneNumber").getValue().toString());
//                        textViewProfileEmail.setText(snapshot.child("email").getValue().toString());
//                        textViewProfileName.setText(snapshot.child("name").getValue().toString());
//                        studentId = snapshot.child("studentId").getValue().toString();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//
//        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
//        databaseReference.child("item_list").addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String id = dataSnapshot.getValue().toString();
//                String name = dataSnapshot.child("name").getValue().toString();
//                String icon = dataSnapshot.child("icon").getValue().toString();
//                String deadlineDate = dataSnapshot.child("deadlineDate").getValue().toString();
//                String content = dataSnapshot.child("content").getValue().toString();
//                String targetNum = dataSnapshot.child("targetNum").getValue().toString();
//                String currNum = dataSnapshot.child("currNum").getValue().toString();
//                String price = dataSnapshot.child("price").getValue().toString();
//                String discountPrice = dataSnapshot.child("discountPrice").getValue().toString();
//                String creationDate = dataSnapshot.child("creationDate").getValue().toString();
//
//                adapter.addItem(id, name, icon, deadlineDate,  content, targetNum, currNum, price, discountPrice, creationDate);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
