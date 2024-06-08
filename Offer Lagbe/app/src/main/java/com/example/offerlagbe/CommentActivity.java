package com.example.offerlagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.offerlagbe.Fragment.User;
import com.example.offerlagbe.Model.Comment;
import com.example.offerlagbe.Model.Post;
import com.example.offerlagbe.databinding.ActivityCommentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    ActivityCommentBinding binding;
    Intent intent;
    String postId;
    String postedBy;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");

        database.getReference()
                .child("posts")
                .child(postId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Post post = snapshot.getValue(Post.class);
                        Picasso.get()
                                .load(post.getPostImage())
                                .placeholder(R.drawable.placeholder)
                                .into(binding.postImage);
                        binding.description.setText(post.getPostDescription());
                        binding.like.setText(String.valueOf(post.getPostLike()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        database.getReference()
                .child("Users")
                .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User  user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.placeholder)
                                .into(binding.profileImage);
                        binding.name.setText(user.getName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        binding.commentPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comment comment = new Comment();
                comment.setCommentBody(binding.commentET.getText().toString());
                comment.setCommentedAt(new Date().getTime());
                comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

                database.getReference()
                        .child("posts")
                        .child(postId)
                        .child("comments");
            }
        });

        }
}
