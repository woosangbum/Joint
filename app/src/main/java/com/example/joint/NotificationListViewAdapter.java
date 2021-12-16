package com.example.joint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationListViewAdapter extends BaseAdapter {
    private ArrayList<Notification> listViewItemList = new ArrayList<Notification>() ;


    public void ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
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
            convertView = inflater.inflate(R.layout.listview_notification, parent, false);
        }

        TextView contentTextView = (TextView) convertView.findViewById(R.id.view_notification_content) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.view_notification_date) ;

        Notification listViewItem = listViewItemList.get(position);

        contentTextView.setText(listViewItem.getContent());
        dateTextView.setText(listViewItem.getDate());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String date, String content, String studentId) {
        Notification item = new Notification(date, content, studentId);
        listViewItemList.add(item);
    }
}