package com.example.joint;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoticeListViewAdapter extends BaseAdapter {
    private ArrayList<NoticeItem> listViewItemList = new ArrayList<NoticeItem>() ;


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
            convertView = inflater.inflate(R.layout.listview_notice_item, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date) ;

        NoticeItem listViewItem = listViewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
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
    public String getItemTitle() {
        return listViewItemList.get(0).getTitle() ;
    }

    public void addItem(String id, String title, String date, String content) {
        NoticeItem item = new NoticeItem();

        item.setId(id);
        item.setTitle(title);
        item.setDate(date);
        item.setContent(content);

        listViewItemList.add(item);
    }
}