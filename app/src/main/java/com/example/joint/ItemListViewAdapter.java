package com.example.joint;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemListViewAdapter extends BaseAdapter {
    private ArrayList<Item> listViewItemList = new ArrayList<Item>();
    private Activity activity;
    private int noCnt = 0;

    public ItemListViewAdapter(Activity activity) {
        this.activity = activity;
    }

//    Context context;

//    public void ListViewAdapter(Context context) {
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    public String getCountString() {
        return String.valueOf(listViewItemList.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.itemImageView);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView deadlineDateTextView = (TextView) convertView.findViewById(R.id.deadlinedate);
        Button editPostButton = (Button) convertView.findViewById(R.id.editPostButton);
        Button deletedButton = (Button) convertView.findViewById(R.id.deletedButton);


        Item listViewItem = listViewItemList.get(position);
        nameTextView.setText(listViewItem.getName());
        deadlineDateTextView.setText(listViewItem.getDeadlineDate());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storageRef.child(listViewItem.getIcon()).getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Log.d("aaaa", listViewItem.getIcon());
                    Glide.with(context.getApplicationContext())
                            .load(uri)
                            .into(imageView);
                }).addOnFailureListener(exception -> {
            Log.d("aaaa", "이미지 불러오기 실패");
            Toast.makeText(context.getApplicationContext(), "이미지 실패", Toast.LENGTH_SHORT).show();
        });

        if (listViewItem.getCurrNum().equals(listViewItem.getTargetNum())) {
            convertView.setBackgroundColor(Color.GRAY);
            nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            deadlineDateTextView.setPaintFlags(deadlineDateTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        String checkStudentId = PreferenceManager.getString(context, "studentId");

        if (!checkStudentId.equals("root")) {
            editPostButton.setVisibility(View.INVISIBLE);
            deletedButton.setVisibility(View.INVISIBLE);

            editPostButton.setEnabled(false);
            deletedButton.setEnabled(false);
        }

        editPostButton.setOnClickListener(new Button.OnClickListener() { // 수정
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "수정", Toast.LENGTH_SHORT).show();
            }
        });

        // 삭제
        deletedButton.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            DialogInterface.OnClickListener delete = (dialog, which) -> {
                Toast.makeText(context.getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                String itemId = listViewItem.getId();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("item_list").child(itemId);
                databaseReference.removeValue();

                //유저에게 알림 보내기
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("id_cnt_list");
                ref.child("notificationCnt").get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        noCnt = (Integer) task.getResult().getValue();
                    }
                });
                String studentId = PreferenceManager.getString(context.getApplicationContext(), "studentId");
                DatabaseReference reff = database.getReference("notification_list");
                DatabaseReference hopperReff = reff.child("notification" + noCnt);
                Map<String, Object> hopperUpdate = new HashMap<>();
                hopperUpdate.put("content", "게시글이 삭제되었습니다.");
                hopperUpdate.put("date", LocalDate.now());
                hopperUpdate.put("studentId", studentId);
                hopperReff.updateChildren(hopperUpdate);
                noCnt++;

                reff = database.getReference("id_cnt_list");
                Map<String, Object> hopperUpdateCnt = new HashMap<>();
                hopperUpdateCnt.put("notificationCnt", String.valueOf(noCnt));
                reff.updateChildren(hopperUpdateCnt);

                FirebaseDatabase.getInstance().getReference().child("user_purchase").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (itemId.equals(snapshot.child("itemId").getValue().toString())) {
                                DatabaseReference databaseReference = firebaseDatabase.getReference("user_purchase").child(snapshot.getKey());
                                databaseReference.removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }


                });
                Intent intent = ((Activity) context).getIntent();
                ((Activity) context).finish(); //현재 액티비티 종료 실시
                ((Activity) context).overridePendingTransition(0, 0); //효과 없애기
                ((Activity) context).startActivity(intent); //현재 액티비티 재실행 실시
                ((Activity) context).overridePendingTransition(0, 0); //효과 없애기
            };

            DialogInterface.OnClickListener cancel = (dialog, which) -> Toast.makeText(context.getApplicationContext(), "취소 테스트", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(activity)
                    .setTitle("삭제하시겠습니까?")
                    .setPositiveButton("삭제", delete)
                    .setNegativeButton("취소", cancel)
                    .show();
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    public void addItem(String id, String name, String icon, String deadlineDate,
                        String content, String targetNum, String currNum, String price, String discountPrice, String creationDate) {
        Item item = new Item(id, name, icon, deadlineDate, content, targetNum, currNum, price, discountPrice, creationDate);

        listViewItemList.add(item);
    }
}