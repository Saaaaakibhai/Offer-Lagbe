package com.example.offerlagbe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.offerlagbe.Adapter.CommentAdapter;
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

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    ActivityCommentBinding binding;
    Intent intent;
    String postId;
    String postedBy;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<Comment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();

        setSupportActionBar(binding.toolbar);
        CommentActivity.this.setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");

        // Load post details
        database.getReference()
                .child("posts")
                .child(postId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Post post = snapshot.getValue(Post.class);
                        if (post != null) {
                            Picasso.get()
                                    .load(post.getPostImage())
                                    .placeholder(R.drawable.placeholder)
                                    .into(binding.postImage);
                            binding.description.setText(post.getPostDescription());
                            binding.like.setText(String.valueOf(post.getPostId()));
                            binding.comment.setText(String.valueOf(post.getCommentCount()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        // Load user details
        database.getReference()
                .child("Users")
                .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            Picasso.get()
                                    .load(user.getProfile())
                                    .placeholder(R.drawable.placeholder)
                                    .into(binding.profileImage);
                            binding.name.setText(user.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        // Post a comment
        binding.commentPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = binding.commentET.getText().toString().trim();
                if (!commentText.isEmpty()) {
                    Comment comment = new Comment();
                    comment.setCommentBody(commentText);
                    comment.setCommentedAt(new Date().getTime());
                    comment.setCommentedBy(auth.getUid());

                    database.getReference()
                            .child("posts")
                            .child(postId)
                            .child("comments")
                            .push() // create a new child for new user new comment
                            .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    database.getReference()
                                            .child("posts")
                                            .child(postId)
                                            .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    int commentCount = 0;
                                                    if (snapshot.exists()) {
                                                        commentCount = snapshot.getValue(Integer.class);
                                                    }
                                                    database.getReference()
                                                            .child("posts")
                                                            .child(postId)
                                                            .child("commentCount")
                                                            .setValue(commentCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    binding.commentET.setText(""); // clear the comment input
                                                                    Toast.makeText(CommentActivity.this, "Commented", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                }
                            });
                } else {
                    Toast.makeText(CommentActivity.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up the RecyclerView for comments
        CommentAdapter adapter = new CommentAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commentRv.setLayoutManager(layoutManager);
        binding.commentRv.setAdapter(adapter);

        // Load comments
        database.getReference()
                .child("posts")
                .child(postId)
                .child("comments").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear(); // Clear the list before adding new comments
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Comment comment = dataSnapshot.getValue(Comment.class);
                            if (comment != null) {
                                list.add(comment);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
