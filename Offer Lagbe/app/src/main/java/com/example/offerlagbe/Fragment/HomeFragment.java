package com.example.offerlagbe.Fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.offerlagbe.Adapter.PostAdapter;
import com.example.offerlagbe.Adapter.StoryAdapter;
import com.example.offerlagbe.Model.Post;
import com.example.offerlagbe.Model.StoryModel;
import com.example.offerlagbe.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView storyRv,dashboardRv;
    ArrayList<StoryModel> list;
    ArrayList<Post> postList;
    ImageView addStory;
    FirebaseDatabase database;
    FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        storyRv = view.findViewById(R.id.storyRV);
        list = new ArrayList<>();

        //Story,Story Type(live or normal),profile,string name
        list.add(new StoryModel(R.drawable.dennis,R.drawable.ic_video_camera,R.drawable.deaf,"Bata"));
        list.add(new StoryModel(R.drawable.dennis,R.drawable.live,R.drawable.deaf,"Sulekho"));
        list.add(new StoryModel(R.drawable.dennis,R.drawable.ic_video_camera,R.drawable.deaf,"Sulekho"));
        list.add(new StoryModel(R.drawable.dennis,R.drawable.ic_video_camera,R.drawable.deaf,"Sulekho"));
        list.add(new StoryModel(R.drawable.dennis,R.drawable.ic_video_camera,R.drawable.deaf,"Sulekho"));


        StoryAdapter adapter = new StoryAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRv.setLayoutManager(linearLayoutManager);
        storyRv.setNestedScrollingEnabled(false);
        storyRv.setAdapter(adapter);


        dashboardRv = view.findViewById(R.id.dashboardRV);
        postList = new ArrayList<>();


        PostAdapter postAdapter = new PostAdapter(postList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRv.setLayoutManager(layoutManager);
        //dashboardRv.addItemDecoration(new DividerItemDecoration(dashboardRv.getContext().Divider));
        dashboardRv.setNestedScrollingEnabled(false);
        dashboardRv.setAdapter(postAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}
