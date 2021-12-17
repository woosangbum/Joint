package com.example.joint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootOrderStudentListActivity extends AppCompatActivity {
    public static Context context;

    private ListView student_list_view;
    StudentListAdapter adapter;
    String itemId;
    String itemName;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    DatabaseReference ref;
    DatabaseReference refCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ref = firebaseDatabase.getReference("notification_list");
        refCnt = firebaseDatabase.getReference("id_cnt_list");

        TextView tvName = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        itemName = intent.getStringExtra("name");
        tvName.setText(intent.getStringExtra("name"));
        itemId = intent.getStringExtra("id");

        student_list_view = (ListView) findViewById(R.id.studentListView);
        showStudentList();
    }


    public void showStudentList() {
        adapter = new StudentListAdapter();
        student_list_view.setAdapter(adapter);
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("user_purchase").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                String purId = dataSnapshot.child("itemId").getValue().toString();
                String purStudentId = dataSnapshot.child("studentId").getValue().toString();
                String purProductCount = dataSnapshot.child("productCount").getValue().toString();
                String isChecked = dataSnapshot.child("isReceipt").getValue().toString();
                if (itemId.equals(purId)) {
                    databaseReference.child("user").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String studentId = dataSnapshot.getKey();
                            if (studentId.equals(purStudentId)) {
                                String name = dataSnapshot.child("name").getValue().toString();
                                adapter.addItem(name, studentId, purProductCount, isChecked, id);
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

    public void onRootOrder(View view) { // 주문하기 클릭
        int noCnt = Integer.valueOf(PreferenceManager.getString(getApplicationContext(), "notificationCnt"));

        ArrayList<HistoryStudent> listViewOrderList = adapter.getStudentList();

        if (listViewOrderList.size() == 0) {
            Toast.makeText(RootOrderStudentListActivity.this, "주문 실패", Toast.LENGTH_SHORT).show();
            return;
        }

        for (HistoryStudent s : listViewOrderList) {
            DatabaseReference hopperRef = ref.child("notification" + noCnt);
            Map<String, Object> hopperUpdates = new HashMap<>();

            String content = itemName + " 주문이 시작되었습니다.";
            String creationDate = LocalDate.now().getYear() + "년 " + LocalDate.now().getMonthValue() + "월 " +
                    LocalDate.now().getDayOfMonth() + "일";

            hopperUpdates.put("studentId", String.valueOf(s.getStudentId()));
            hopperUpdates.put("content", String.valueOf(content));
            hopperUpdates.put("date", String.valueOf(creationDate));

            hopperRef.updateChildren(hopperUpdates);
            noCnt++;

        }
        PreferenceManager.setString(getApplicationContext(), "notificationCnt", String.valueOf(noCnt));
        Map<String, Object> hopperUpdateCnt = new HashMap<>();
        hopperUpdateCnt.put("notificationCnt", String.valueOf(noCnt));
        refCnt.updateChildren(hopperUpdateCnt);

        Toast.makeText(RootOrderStudentListActivity.this, "주문 성공", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(RootOrderStudentListActivity.this, RootOrderHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
