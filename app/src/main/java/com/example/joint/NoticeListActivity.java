package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeListActivity extends AppCompatActivity {
    // 공시사항 리스트

    private ListView notice_view;
    NoticeListViewAdapter adapter;
//    ArrayAdapter<NoticeItem> adapter;
//    SimpleAdapter adapter;
//    String [] keys ={"title","date"};
//    int [] ids = {android.R.id.text1,android.R.id.text2};
//    ArrayList<HashMap<String,String>> noticeList = new ArrayList<HashMap<String,String>>();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        notice_view = (ListView) findViewById(R.id.noticeListView);
        showNoticeList();
    }

    private void showNoticeList() {
//        adapter = new ArrayAdapter<NoticeItem>(this, android.R.layout.simple_list_item_1);
//        notice_view.setAdapter(adapter);
//        adapter = new SimpleAdapter(this,noticeList,android.R.layout.simple_list_item_2, keys, ids);
        adapter = new NoticeListViewAdapter();
        notice_view.setAdapter(adapter);
        notice_view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("notice_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String title = dataSnapshot.child("title").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
//                HashMap<String,String> item = new HashMap<String, String>();
//                item.put("title", title);
//                item.put("date", date);
//                noticeList.add(item);
//                Log.d("list", noticeList.toString());
                adapter.addItem(title, date);
                Log.d("ad", adapter.getItem(0).toString());
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




    public void onClickNotice(View v){
        Intent intent = new Intent(NoticeListActivity.this, NoticeListActivity.class);
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