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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserPurchaseListViewAdapter extends BaseAdapter {
    private ArrayList<UserPurchase> listViewUserPurchaseList = new ArrayList<>() ;
    Context context;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    String productName;
    String productIcon;

    public void UserPurchaseListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewUserPurchaseList.size() ;
    }

    // 이미지, 물품id -> 물품 아이콘, 물품 이름, 물품 텍스트, 구매날짜, 개수
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_purchase, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.purchaseImageView);
        TextView purchaseNameTextView = (TextView) convertView.findViewById(R.id.purchaseName);
        TextView purchasePriceTextView = (TextView) convertView.findViewById(R.id.purchasePrice);
        TextView purchaseCountTextView = (TextView) convertView.findViewById(R.id.purchaseCount);

        UserPurchase listViewPurchase = listViewUserPurchaseList.get(position);

        String itemId = databaseReference.child("item_list").child(listViewPurchase.getItemId()).getKey();

        databaseReference.child("item_list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                if(id.equals(itemId)) {
                    productName = dataSnapshot.child("name").getValue().toString();
                    productIcon = dataSnapshot.child("icon").getValue().toString();

                    purchaseNameTextView.setText(productName);
                    purchasePriceTextView.setText(listViewPurchase.getProductPrice());
                    purchaseCountTextView.setText(listViewPurchase.getProductCount());

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    storageRef.child(productIcon).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(context.getApplicationContext())
                                            .load(uri)
                                            .into(imageView);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(context.getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
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

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return listViewUserPurchaseList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    public void addItem(String id, String studentId, String productId, String productCount,
                        String productPrice, String isReceipt, String purchaseDate) {
        UserPurchase userPurchase = new UserPurchase(id, studentId, productId, productCount,
                productPrice, isReceipt, purchaseDate);

        listViewUserPurchaseList.add(userPurchase);
    }

}
