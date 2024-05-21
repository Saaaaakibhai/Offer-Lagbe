package com.example.offerlagbe.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.offerlagbe.Adapter.NotificationAdapter;
import com.example.offerlagbe.Model.NotificationModel;
import com.example.offerlagbe.R;

import java.util.ArrayList;


public class Notification2Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NotificationModel> list;


    public Notification2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);

        recyclerView = view.findViewById(R.id.notification2RV);

        list = new ArrayList<>();
        list.add(new NotificationModel(R.drawable.notificationprofiletaj,"<b>Taj Talukdar</b> mentioned you in a comment: Nice Dress",
                "just now"));
        list.add(new NotificationModel(R.drawable.notificationprofiletoha,"<b>Toha Suvha</b> liked you picture.",
                "40 minutes ago"));
        list.add(new NotificationModel(R.drawable.notificationprofileeshika,"<b>Eshika</b> commented on your post.",
                "2 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilefaria,"<b>Faria Chowdhury</b> mentioned you in a comment: beautiful shoes",
                "3 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilehridoy,"<b>Hridoy</b> mentioned you in a comment: i love it ",
                "3 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilerana,"<b>Rana</b> mentioned you in a comment: good product",
                "4 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilerefat,"<b>Tahsin Refat</b> mentioned you in a comment: love it",
                "6 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilerimon,"<b>Rimon</b> mentioned you in a comment: peaky onek joss product dey",
                "6 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofilerohan,"<b>Yash Rohan</b> mentioned you in a comment: i will repurchase from here",
                "9 hours"));
        list.add(new NotificationModel(R.drawable.notificationprofileshezan,"<b>Shezan Khan</b> liked your cover photo",
                "yesterday"));
        list.add(new NotificationModel(R.drawable.notificationprofileuday,"<b>Uday Maruf</b> mentioned you in a comment: nice shoes",
                "yesterday"));

        NotificationAdapter adapter = new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }
}