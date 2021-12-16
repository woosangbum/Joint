package com.example.joint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;

public class NoticeRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private static int noticeListCnt = 1;

    //define view objects
    EditText editTextRegisterTitle; //제목
    EditText editTextContent; //내용

    Button noticeRegisterPostButton; //등록 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        //initializing views
        editTextRegisterTitle = (EditText) findViewById(R.id.editTextRegisterTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);

        noticeRegisterPostButton = (Button)findViewById(R.id.noticeRegisterPostButton);

        //button click event
        noticeRegisterPostButton.setOnClickListener((View.OnClickListener)this);
    }

    //공지사항 게시물 등록
    private void registerNoticePost(){
        String title = editTextRegisterTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String id = "notice" + String.valueOf(noticeListCnt);


        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(content)){
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = LocalDate.now().getYear() + "년 " + LocalDate.now().getMonthValue() + "월 " +
                LocalDate.now().getDayOfMonth() + "일 "+ LocalTime.now().getHour() + "시 " + LocalTime.now().getMinute() + "분";
        NoticeItem noticeItem = new NoticeItem(title, date, content);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("notice_list");
        reference.child(id).setValue(noticeItem);
        noticeListCnt++;

        Toast.makeText(NoticeRegisterActivity.this, "등록 성공", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(getApplicationContext(), NoticeListActivity.class));
    }

    //button click event
    @Override
    public void onClick(View view) {
        if(view == noticeRegisterPostButton) {
            registerNoticePost(); 
        }
    }
}
