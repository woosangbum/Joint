package com.example.joint;

import android.app.Activity;
import android.content.Context;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ItemListActivity extends AppCompatActivity{
    // 물품 리스트
    public static Context context;

    private ListView item_view;
    ItemListViewAdapter adapter;

    Button itemRegisterButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    DatabaseReference refCnt;

    // Front End (bottom_menu)
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        refCnt = firebaseDatabase.getReference("id_cnt_list");

        context = this;
        refCnt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PreferenceManager.setString(getApplicationContext(), "itemCnt", snapshot.child("itemCnt").getValue().toString());
                PreferenceManager.setString(getApplicationContext(), "noticeCnt", snapshot.child("noticeCnt").getValue().toString());
                PreferenceManager.setString(getApplicationContext(), "notificationCnt", snapshot.child("notificationCnt").getValue().toString());
                PreferenceManager.setString(getApplicationContext(), "purchaseCnt", snapshot.child("purchaseCnt").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String studentId = PreferenceManager.getString(getApplicationContext(), "studentId");
        if(!studentId.equals("root")) {
            itemRegisterButton = findViewById(R.id.itemRegisterButton);
            itemRegisterButton.setVisibility(View.INVISIBLE);
            itemRegisterButton.setEnabled(false);
        }

        item_view = (ListView) findViewById(R.id.itemListView);
        showItemList();
        item_view.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("ddddd", "Clicked");
            Intent intent = new Intent(ItemListActivity.this, ItemActivity.class);

            // intent 객체에 데이터를 실어서 보내기
            Item item = (Item) adapter.getItem(position);
            intent.putExtra("name", item.getName());
            intent.putExtra("icon", item.getIcon());
            intent.putExtra("deadlineDate", item.getDeadlineDate());
            intent.putExtra("content", item.getContent());
            intent.putExtra("targetNum", item.getTargetNum());
            intent.putExtra("currNum", item.getCurrNum());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("discountPrice", item.getDiscountPrice());
            intent.putExtra("creationDate", item.getCreationDate());
            intent.putExtra("itemId", item.getId());
            finish();
            startActivity(intent);
        });
        Log.d("ddddd", "2");
        // Front End
        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.action_home:
                        intent = new Intent(ItemListActivity.this, ItemListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_notice:
                        intent = new Intent(ItemListActivity.this, NoticeListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_profile:
                        intent = new Intent(ItemListActivity.this, MyprofileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_notification:
                        intent = new Intent(ItemListActivity.this, NotificationActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }


    public void showItemList() {
        adapter = new ItemListViewAdapter(this);
        item_view.setAdapter(adapter);
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("item_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                String name = dataSnapshot.child("name").getValue().toString();
                String icon = dataSnapshot.child("icon").getValue().toString();
                String deadlineDate = dataSnapshot.child("deadlineDate").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();
                String targetNum = dataSnapshot.child("targetNum").getValue().toString();
                String currNum = dataSnapshot.child("currNum").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();
                String discountPrice = dataSnapshot.child("discountPrice").getValue().toString();
                String creationDate = dataSnapshot.child("creationDate").getValue().toString();

                if(!IsOverDeadLineDate(id, deadlineDate)) {
                    adapter.addItem(id, name, icon, deadlineDate, content, targetNum, currNum, price, discountPrice, creationDate);
                    adapter.notifyDataSetChanged();
                }
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
    public boolean IsOverDeadLineDate(String id, String deadlineDate) {
        int cmpYear = Integer.valueOf(deadlineDate.split(" ")[0].replace("년", ""));
        int cmpMonth = Integer.valueOf(deadlineDate.split(" ")[1].replace("월", ""));
        int cmpDay = Integer.valueOf(deadlineDate.split(" ")[2].replace("일", ""));
        int currYear = LocalDate.now().getYear();
        int currMonth = LocalDate.now().getMonthValue();
        int currDay = LocalDate.now().getDayOfMonth();
        if (cmpYear <= currYear && cmpMonth <= currMonth && cmpDay < currDay) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("item_list").child(id);
            databaseReference.removeValue();

            //user_purchase순회하면서 해당 아이템 들어간 product들 지우기
            FirebaseDatabase.getInstance().getReference().child("user_purchase").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (id.equals(snapshot.child("itemId").getValue().toString())) {
                            DatabaseReference databaseReference = firebaseDatabase.getReference("user_purchase").child(snapshot.getKey());
                            databaseReference.removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return true;
        }
        return false;
    }

    public void addPost(View v){
        Intent intent = new Intent(ItemListActivity.this, ItemRegisterActivity.class);
        startActivity(intent);
    }

//    public void onClickHome(View v){
//        Intent intent = new Intent(ItemListActivity.this, ItemListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotice(View v){
//        Intent intent = new Intent(ItemListActivity.this, NoticeListActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickMyProfile(View v){
//        Intent intent = new Intent(ItemListActivity.this, MyprofileActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickNotification(View v){
//        Intent intent = new Intent(ItemListActivity.this, NotificationActivity.class);
//        startActivity(intent);
//    }
}