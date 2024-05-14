package com.example.offerlagbe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offerlagbe.Model.Follow;
import com.example.offerlagbe.R;
import com.example.offerlagbe.databinding.FriendRvSampleBinding;

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
        Follow model = list.get(position);
        // Set profile image or any other necessary data here
        // For example:
        // holder.binding.profileImage.setImageResource(model.getProfile());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        // ImageView profile;
        FriendRvSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            // profile = itemView.findViewById(R.id.profileImage);
            binding = FriendRvSampleBinding.bind(itemView);
        }
    }
}
