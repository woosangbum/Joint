package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    UserPurchaseListViewAdapter adapter;
    // Front End (bottom_menu)
    private BottomNavigationView bottomNavigationView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_purchase_history_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

//        Intent intent = getIntent();
//        studentId = intent.getStringExtra("student_id");

        userPurchaseListView = (ListView) findViewById(R.id.userPurchaseListView);
        Log.d("purchase", "1");
        showPurchaseList();
        userPurchaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent( getApplicationContext(), .class);
//
//                startActivity(intent);
            }
        });
        // Front End
        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.action_home:
                        intent = new Intent(UserPurchaseHistoryActivity.this, ItemListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_notice:
                        intent = new Intent(UserPurchaseHistoryActivity.this, NoticeListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_profile:
                        intent = new Intent(UserPurchaseHistoryActivity.this, MyprofileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_notification:
                        intent = new Intent(UserPurchaseHistoryActivity.this, NotificationActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }
    private void showPurchaseList() {
        adapter = new UserPurchaseListViewAdapter();
        userPurchaseListView.setAdapter(adapter);
        Log.d("purchase", "2");
//         데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("user_purchase").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String checkStudentid = PreferenceManager.getString(getApplicationContext(), "studentId");

                // id, studentId, productId, productCount, productPrice, isReceipt, purchaseDate
                String id = dataSnapshot.getKey();
                String studentId = dataSnapshot.child("studentId").getValue().toString();

                if(!studentId.equals(checkStudentid)) return;

                String productId = dataSnapshot.child("itemId").getValue().toString();
                String productCount = dataSnapshot.child("productCount").getValue().toString();
                String productPrice = dataSnapshot.child("productPrice").getValue().toString();
                String isReceipt = dataSnapshot.child("isReceipt").getValue().toString();
                String purchaseDate = dataSnapshot.child("purchaseDate").getValue().toString();

                adapter.addItem(id, studentId, productId, productCount, productPrice, isReceipt, purchaseDate);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public void onClickHome(View v){
//        Intent intent = new Intent(UserPurchaseHistoryActivity.this, ItemListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotice(View v){
//        Intent intent = new Intent(UserPurchaseHistoryActivity.this, NoticeListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickMyProfile(View v){
//        Intent intent = new Intent(UserPurchaseHistoryActivity.this, MyprofileActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotification(View v){
//        Intent intent = new Intent(UserPurchaseHistoryActivity.this, NotificationActivity.class);
//        startActivity(intent);
//    }


}
