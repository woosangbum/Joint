package com.example.joint;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {
    private ArrayList<User> listViewOrderList = new ArrayList<User>() ;
//    Context context;

//    public void ListViewAdapter(Context context) {
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return listViewOrderList.size() ;
    }
    public String getCountString() {
        return String.valueOf(listViewOrderList.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_student, parent, false);
        }

        TextView idTextView = (TextView) convertView.findViewById(R.id.view_student_id) ;
        TextView nameTextView = (TextView) convertView.findViewById(R.id.view_student_name) ;


        User listViewItem = listViewOrderList.get(position);
        idTextView.setText(listViewItem.getStudentId());
        nameTextView.setText(listViewItem.getName());

        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewOrderList.get(position) ;
    }

    public void addItem(String name, String phoneNumber, String email, String studentId) {
        User user = new User(name, phoneNumber, email, studentId);

        listViewOrderList.add(user);
    }
}