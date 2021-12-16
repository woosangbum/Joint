package com.example.joint;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentListAdapter extends BaseAdapter {
    private ArrayList<HistoryStudent> listViewOrderList = new ArrayList<HistoryStudent>() ;

//    Context context;

//    public void ListViewAdapter(Context context) {
//        this.context = context;
//    }

    public ArrayList<HistoryStudent> getStudentList(){
        return listViewOrderList;
    }

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
        TextView cntTextView = (TextView) convertView.findViewById(R.id.view_product_count) ;
//        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);

        HistoryStudent listViewItem = listViewOrderList.get(position);
        idTextView.setText(listViewItem.getStudentId());
        nameTextView.setText(listViewItem.getName());
        cntTextView.setText(listViewItem.getProductCount());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user_purchase");
        DatabaseReference hopperRef = ref.child(listViewItem.getProductId());
        Map<String, Object> hopperUpdates = new HashMap<>();

//        if(listViewItem.getIsChecked().equals("true")) checkBox.setChecked(true);
//        else checkBox.setChecked(false);
//
//        checkBox.setOnCheckedChangeListener(null);
//
//        if(listViewItem.getIsChecked().equals("true")) checkBox.setChecked(true);
//        else checkBox.setChecked(false);
//        checkBox.setChecked(true);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                if(checkBox.isChecked()){
//                    hopperUpdates.put("isReceipt",  String.valueOf("true"));
//                    hopperRef.updateChildren(hopperUpdates);
//                }
//                else {
//                    hopperUpdates.put("isReceipt",  String.valueOf("false"));
//                    hopperRef.updateChildren(hopperUpdates);
//                }
//            }
//        });

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

    public void addItem(String name, String studentId, String productCount, String isChecked, String productId) {
        HistoryStudent historyStudent = new HistoryStudent(name, studentId, productCount, isChecked, productId);

        listViewOrderList.add(historyStudent);
    }
}