package com.example.joint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RootOrderStudentListActivity extends AppCompatActivity {
    public static Context context;

    private ListView student_list_view;
    StudentListAdapter adapter;
    String itemId;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        TextView tvName = (TextView)findViewById(R.id.textView3);

        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvName.setText(intent.getStringExtra("name"));
        itemId  = intent.getStringExtra("id");

        student_list_view = (ListView) findViewById(R.id.studentListView);
        showStudentList2();
    }

    public void showStudentList2() {
        adapter = new StudentListAdapter();
        student_list_view.setAdapter(adapter);
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("user_purchase").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String purId = dataSnapshot.child("itemId").getValue().toString();
                String purStudentId = dataSnapshot.child("studentId").getValue().toString();
                String purProductCount = dataSnapshot.child("productCount").getValue().toString();
                if(itemId.equals(purId)){
                    databaseReference.child("user").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String studentId = dataSnapshot.getKey();
                            if(studentId.equals(purStudentId)) {
                                String name = dataSnapshot.child("name").getValue().toString();
                                adapter.addItem(name, studentId, purProductCount);
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

//                adapter.addItem(name, phoneNumber, email, studentId);
//                adapter.notifyDataSetChanged();
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

    public void onRootOrder(View view){ // 주문하기 클릭

    }
}
