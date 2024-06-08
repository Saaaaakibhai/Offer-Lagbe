package com.example.offerlagbe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offerlagbe.CommentActivity;
import com.example.offerlagbe.Model.Post;
import com.example.offerlagbe.Model.User;
import com.example.offerlagbe.R;
import com.example.offerlagbe.databinding.DashboardRvSampleBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {

    ArrayList<Post> list;
    Context context;

    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample,parent,false);
        return new viewHolder(view);
    }
    //Setting data
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Post model = list.get(position);
        Picasso.get()
                .load(model.getPostImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.postImg);
        holder.binding.like.setText(model.getPostLike() + "");

        String description = model.getPostDescription();
        if (description == null || description.equals("")) {
            holder.binding.postDescription.setVisibility(View.GONE);
        } else {
            holder.binding.postDescription.setText(description);
            holder.binding.postDescription.setVisibility(View.VISIBLE);
        }

        String postedBy = model.getPostedBy();
        if (postedBy != null) {
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(postedBy).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                Picasso.get()
                                        .load(user.getProfile())
                                        .placeholder(R.drawable.placeholder)
                                        .into(holder.binding.profileImage);
                                holder.binding.brandUserName.setText(user.getName());
                                holder.binding.about.setText(user.getCompanyname());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(model.getPostId())
                .child("likes")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_2, 0, 0, 0);
                        } else {
                            holder.binding.like.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String userId = FirebaseAuth.getInstance().getUid();
                                    if (userId != null && model.getPostId() != null) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("posts")
                                                .child(model.getPostId())
                                                .child("likes")
                                                .child(userId)
                                                .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        if (model.getPostedBy() != null) {
                                                            FirebaseDatabase.getInstance().getReference()
                                                                    .child("posts")
                                                                    .child(model.getPostedBy())
                                                                    .child("postLike")
                                                                    .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            holder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_2, 0, 0, 0);
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId", model.getPostId());
                intent.putExtra("postedBy", model.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Hold all image
        DashboardRvSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=DashboardRvSampleBinding.bind(itemView);
        }
    }
}
