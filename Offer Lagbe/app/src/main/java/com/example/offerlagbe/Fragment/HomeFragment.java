package com.example.offerlagbe.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.offerlagbe.Adapter.PostAdapter;
import com.example.offerlagbe.Adapter.StoryAdapter;
import com.example.offerlagbe.Model.Post;
import com.example.offerlagbe.Model.Story;
import com.example.offerlagbe.Model.UserStories;
import com.example.offerlagbe.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    RecyclerView storyRv, dashboardRv;
    ArrayList<Story> list;
    ArrayList<Post> postList;
    ImageView addStory;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RoundedImageView addStoryImage;
    ActivityResultLauncher<String> galleryLauncher;

    FirebaseStorage storage;

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
        storage = FirebaseStorage.getInstance();

        storyRv = view.findViewById(R.id.storyRV);
        list = new ArrayList<>();

        // Story, Story Type (live or normal), profile, string name
        StoryAdapter adapter = new StoryAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        storyRv.setLayoutManager(linearLayoutManager);
        storyRv.setNestedScrollingEnabled(false);
        storyRv.setAdapter(adapter);

        dashboardRv = view.findViewById(R.id.dashboardRV);
        postList = new ArrayList<>();

        PostAdapter postAdapter = new PostAdapter(postList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRv.setLayoutManager(layoutManager);
        dashboardRv.setNestedScrollingEnabled(false);
        dashboardRv.setAdapter(postAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post != null) {
                        post.setPostId(dataSnapshot.getKey());
                        postList.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        addStoryImage = view.findViewById(R.id.addStoryImg);
        addStoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch("image/*");
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            addStoryImage.setImageURI(result);
                            final StorageReference reference = storage.getReference()
                                    .child("stories")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child(new Date().getTime() + "");
                            reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Story story = new Story();
                                            story.setStoryAt(new Date().getTime());
                                            database.getReference()
                                                    .child("stories")
                                                    .child(FirebaseAuth.getInstance().getUid())
                                                    .child("postedBy")
                                                    .setValue(story.getStoryAt()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            UserStories stories = new UserStories(uri.toString(), story.getStoryAt());
                                                            database.getReference()
                                                                    .child("stories")
                                                                    .child(FirebaseAuth.getInstance().getUid())
                                                                    .child("userStories")
                                                                    .push()
                                                                    .setValue(stories);
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                        } else {
                            // Handle the case where no image was selected
                            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }
}
