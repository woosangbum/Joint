package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeListActivity extends AppCompatActivity {
    // 공시사항 리스트
    private ListView notice_view;
    NoticeListViewAdapter adapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        notice_view = (ListView) findViewById(R.id.noticeListView);
        showNoticeList();

        notice_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), NoticeActivity.class);

                // intent 객체에 데이터를 실어서 보내기
                NoticeItem item = (NoticeItem) adapter.getItem(position);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("date", item.getDate());

                startActivity(intent);
            }
        });

    }



    private void showNoticeList() {
        adapter = new NoticeListViewAdapter();
        notice_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("notice_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getValue().toString();
//                Log.d("id", id);
                String title = dataSnapshot.child("title").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();

                adapter.addItem(title, date, content);
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

    public void noticeRegisterButton(View v){
        Intent intent = new Intent(NoticeListActivity.this, NoticeRegisterActivity.class);
        startActivity(intent);
    }

    public void onClickHome(View v){
        Intent intent = new Intent(NoticeListActivity.this, ItemListActivity.class);
        startActivity(intent);
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