package com.example.joint;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RootOrderStudentListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        TextView tvId = (TextView)findViewById(R.id.textView3);


        Intent intent = getIntent(); // 보내온 Intent를 얻는다
        tvId.setText(intent.getStringExtra("id"));
    }
}
