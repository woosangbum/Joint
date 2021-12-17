package com.example.joint;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class ItemEditActivity extends AppCompatActivity {

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        TextView mT;

        public void setTextView(TextView date) {
            mT = date;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dpDialog =  new DatePickerDialog(getActivity(), this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            dpDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            return dpDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            deadlineDate = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth +"일";
            mT.setText(deadlineDate);
        }
    }

    static String deadlineDate;
    private String itemId;

    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    // realtime db - id, name, deadlineDate, content, targetNum, price, discountPrice
    EditText editTextName; // 제목
    TextView textDeadlineDate; // 마감일자
    EditText editTextContent; // 내용
    EditText editTextTargetNum; // 목표 개수
    EditText editTextPrice; // 정가
    EditText editTextDiscountPrice; // 할인가


    // storage - icon
    private static final int GALLERY_CODE = 10;
    private ImageView photo;
    Uri file;
    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference riversRef;
    Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");

        progressDialog = new ProgressDialog(this);

        // realtime db
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("item_list");

        editTextName = (EditText) findViewById(R.id.editName);
        textDeadlineDate = findViewById(R.id.editDeadlineDateText);
        editTextContent = (EditText) findViewById(R.id.editContent);
        editTextTargetNum = (EditText) findViewById(R.id.editTargetNum);
        editTextPrice = (EditText) findViewById(R.id.editPrice);
        editTextDiscountPrice = (EditText) findViewById(R.id.editDiscountPrice);

        // storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        photo = (ImageView)findViewById(R.id.editUploadImageView);
        storage = FirebaseStorage.getInstance();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                if(id.equals(itemId)) {
                    editTextName.setText(dataSnapshot.child("name").getValue().toString());
                    textDeadlineDate.setText(dataSnapshot.child("deadlineDate").getValue().toString());
                    editTextContent.setText(dataSnapshot.child("content").getValue().toString());
                    editTextTargetNum.setText(dataSnapshot.child("targetNum").getValue().toString());
                    editTextPrice.setText(dataSnapshot.child("price").getValue().toString());
                    editTextDiscountPrice.setText(dataSnapshot.child("discountPrice").getValue().toString());

                    String icon = dataSnapshot.child("icon").getValue().toString();
                    storageRef.child(icon).getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                Glide.with(getApplicationContext())
                                        .load(uri)
                                        .into(photo);
                            }).addOnFailureListener(exception -> {
                        Log.d("aaaa", "이미지 불러오기 실패");
                        Toast.makeText(getApplicationContext(), "이미지 실패", Toast.LENGTH_SHORT).show();
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


    public void onEditDeadlineDateClick(View v){
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setTextView(textDeadlineDate);
        dpf.show(getSupportFragmentManager(), "datePicker");
    }

    public void editRegisterItemPost(View v){
        // id, name, icon, deadlineDate,  content, targetNum, currNum, price, discountPrice, creationDate
        String name = editTextName.getText().toString().trim();
        String icon = itemId + ".png";
        String content = editTextContent.getText().toString().trim();
        String targetNum = editTextTargetNum.getText().toString().trim();
        String currNum = "0";
        String price = editTextPrice.getText().toString().trim();
        String discountPrice = editTextDiscountPrice.getText().toString().trim();
        String creationDate = LocalDate.now().getYear() + "년 " + LocalDate.now().getMonthValue() + "월 " +
                LocalDate.now().getDayOfMonth() + "일 "+ LocalTime.now().getHour() + "시 " + LocalTime.now().getMinute() + "분";

        if(!editTextCheck(name, content, targetNum, price, discountPrice)){
            Log.d("ddddd", "실패");
            return;
        }

        Item item = new Item(itemId, name, icon, deadlineDate, content, targetNum, currNum, price, discountPrice, creationDate);
        reference.child(itemId).setValue(item);

        if(img != null) {
            riversRef = storageRef.child(itemId + ".png");
            Log.d("uri", file.toString());
            UploadTask uploadTask = riversRef.putFile(file);
        }

        ((ItemListActivity) ItemListActivity.context).showItemList();

        Toast.makeText(ItemEditActivity.this, "등록 성공", Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private boolean editTextCheck(String name, String content, String targetNum, String price, String discountPrice){
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(content)){
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(targetNum)){
            Toast.makeText(this, "목표개수를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "정가를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(discountPrice)){
            Toast.makeText(this, "할인가를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if(textDeadlineDate.getText().toString().equals("")) {
//            Toast.makeText(this, "마감일자를 선택해 주세요.", Toast.LENGTH_SHORT).show();
//            return false;
//        }

//        if(img == null){
//            Log.d("ddddd", "사진 넣엇");
//            Toast.makeText(this, "사진을 넣어주세요.", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    public void onEditUploadImage(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {
            file = data.getData();
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                img = BitmapFactory.decodeStream(in);
                in.close();
                photo.setImageBitmap(img);
            } catch (Exception e){
                e.printStackTrace();
            }

//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ItemRegisterActivtiy.this, "사진이 정상적으로 업로드되지 않았습니다.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ItemRegisterActivtiy.this, "사진이 정상적으로 업로드 되었습니다.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}

