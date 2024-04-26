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
        list.add(new NotificationModel(R.drawable.profile,"<b>Jubayer Emon</b> mentioned you in a comment: Nice Try!",
                "just now"));
        list.add(new NotificationModel(R.drawable.deaf,"<b>Billal Mia</b> liked you picture.",
                "40 minutes ago"));
        list.add(new NotificationModel(R.drawable.dennis,"<b>Saddam Hossen</b> commented on your post.",
                "2 hours"));
        list.add(new NotificationModel(R.drawable.homefragmentstory02,"<b>Tonmoy Chowdhury</b> mentioned you in a comment: try again",
                "3 hours"));
        list.add(new NotificationModel(R.drawable.homefragmentstory,"<b>M. Amin</b> mentioned you in a comment: try again",
                "3 hours"));
        list.add(new NotificationModel(R.drawable.profilefragment__profilepicture,"<b>Toukir Ahamed</b> mentioned you in a comment: well done",
                "4 hours"));
        list.add(new NotificationModel(R.drawable.profilefragment__profilecover,"<b>Munia Haque</b> mentioned you in a comment: eta tumi",
                "6 hours"));
        list.add(new NotificationModel(R.drawable.profilefragment__profilepicture2,"<b>Safa Khan</b> mentioned you in a comment: eid mubarak",
                "6 hours"));
        list.add(new NotificationModel(R.drawable.deaf,"<b>Hemon Khan</b> mentioned you in a comment: eta ami",
                "9 hours"));
        list.add(new NotificationModel(R.drawable.deaf,"<b>Farhad Khan</b> liked your cover photo",
                "yesterday"));
        list.add(new NotificationModel(R.drawable.deaf,"<b>Katrina Solaiman</b> mentioned you in a comment: try again",
                "yesterday"));

        NotificationAdapter adapter = new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }
}