package com.example.joint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemListActivity extends AppCompatActivity {
    // 물품 리스트
    public static Context context;

    private ListView item_view;
    ItemListViewAdapter adapter;
    Button itemRegisterButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        context = this;
        //유저의 이메일 가져오기
        FirebaseUser userDB = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = userDB.getEmail().trim();
        //유저의 이메일이 root@koreatech.ac.kr(관리자)가 아니면 게시글 작성 버튼 숨기기
        if(!userEmail.equals(getString(R.string.root))) {
            itemRegisterButton = findViewById(R.id.itemRegisterButton);
            itemRegisterButton.setVisibility(View.INVISIBLE);
            itemRegisterButton.setEnabled(false);
        }

        item_view = (ListView) findViewById(R.id.itemListView);
        showItemList();

        item_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getApplicationContext(), ItemActivity.class);

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

                startActivity(intent);
            }
        });
    }

    public void showItemList() {
        adapter = new ItemListViewAdapter();
        item_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("item_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String icon = dataSnapshot.child("icon").getValue().toString();
                String deadlineDate = dataSnapshot.child("deadlineDate").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();
                String targetNum = dataSnapshot.child("targetNum").getValue().toString();
                String currNum = dataSnapshot.child("currNum").getValue().toString();
                String price = dataSnapshot.child("price").getValue().toString();
                String discountPrice = dataSnapshot.child("discountPrice").getValue().toString();
                String creationDate = dataSnapshot.child("creationDate").getValue().toString();

                adapter.addItem(id, name, icon, deadlineDate,  content, targetNum, currNum, price, discountPrice, creationDate);
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
    public void addPost(View v){
        Intent intent = new Intent(ItemListActivity.this, ItemRegisterActivity.class);
        startActivity(intent);
//        Toast.makeText(getApplicationContext(), "addPost 클릭", Toast.LENGTH_SHORT).show();
    }

    public void onClickHome(View v){
        Intent intent = new Intent(ItemListActivity.this, ItemListActivity.class);
        startActivity(intent);
    }

    public void onClickNotice(View v){
        Intent intent = new Intent(ItemListActivity.this, NoticeListActivity.class);
        startActivity(intent);
    }

    public void onClickMyProfile(View v){
        Intent intent = new Intent(ItemListActivity.this, MyprofileActivity.class);
        startActivity(intent);
    }

    public void onClickNotification(View v){
        Intent intent = new Intent(ItemListActivity.this, NotificationActivity.class);
        startActivity(intent);
    }
}