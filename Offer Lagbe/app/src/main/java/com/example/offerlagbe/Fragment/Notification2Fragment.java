package com.example.offerlagbe.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.offerlagbe.Adapter.NotificationAdapter;
import com.example.offerlagbe.Model.Notification;
import com.example.offerlagbe.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Notification2Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Notification> list;
    FirebaseDatabase database;

    public Notification2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);

        recyclerView = view.findViewById(R.id.notification2RV);

        list = new ArrayList<>();
//        list.add(new Notification(R.drawable.notificationprofiletaj,"<b>Taj Talukdar</b> mentioned you in a comment: Nice Dress",
//                "just now"));
//        list.add(new Notification(R.drawable.notificationprofiletoha,"<b>Toha Suvha</b> liked you picture.",
//                "40 minutes ago"));
//        list.add(new Notification(R.drawable.notificationprofileeshika,"<b>Eshika</b> commented on your post.",
//                "2 hours"));
//        list.add(new Notification(R.drawable.notificationprofilefaria,"<b>Faria Chowdhury</b> mentioned you in a comment: beautiful shoes",
//                "3 hours"));
//        list.add(new Notification(R.drawable.notificationprofilehridoy,"<b>Hridoy</b> mentioned you in a comment: i love it ",
//                "3 hours"));
//        list.add(new Notification(R.drawable.notificationprofilerana,"<b>Rana</b> mentioned you in a comment: good product",
//                "4 hours"));
//        list.add(new Notification(R.drawable.notificationprofilerefat,"<b>Tahsin Refat</b> mentioned you in a comment: love it",
//                "6 hours"));
//        list.add(new Notification(R.drawable.notificationprofilerimon,"<b>Rimon</b> mentioned you in a comment: peaky onek joss product dey",
//                "6 hours"));
//        list.add(new Notification(R.drawable.notificationprofilerohan,"<b>Yash Rohan</b> mentioned you in a comment: i will repurchase from here",
//                "9 hours"));
//        list.add(new Notification(R.drawable.notificationprofileshezan,"<b>Shezan Khan</b> liked your cover photo",
//                "yesterday"));
//        list.add(new Notification(R.drawable.notificationprofileuday,"<b>Uday Maruf</b> mentioned you in a comment: nice shoes",
//                "yesterday"));

        NotificationAdapter adapter = new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        database.getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Notification notification = dataSnapshot.getValue(Notification.class);
                            notification.setNotificationID(dataSnapshot.getKey());
                            list.add(notification);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }
}