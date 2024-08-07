package com.example.offerlagbe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offerlagbe.Model.Follow;
import com.example.offerlagbe.Model.User;
import com.example.offerlagbe.R;
import com.example.offerlagbe.databinding.FriendRvSampleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.viewHolder> {

    ArrayList<Follow> list;
    Context context;

    public FollowersAdapter(ArrayList<Follow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_rv_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Follow follow = list.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(follow.getFollowedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {  // Add null check here
                            if (user.getProfile() != null && !user.getProfile().isEmpty()) {
                                Picasso.get()
                                        .load(user.getProfile())
                                        .placeholder(R.drawable.placeholder)
                                        .into(holder.binding.profileImage);
                            } else {
                                holder.binding.profileImage.setImageResource(R.drawable.placeholder);  // Fallback if profile image URL is empty
                            }
                        } else {
                            // Handle the case where user is null
                            holder.binding.profileImage.setImageResource(R.drawable.placeholder);  // Fallback if user data is missing
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle possible errors
                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        FriendRvSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FriendRvSampleBinding.bind(itemView);
        }
    }
}
