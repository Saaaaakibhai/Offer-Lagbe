package com.example.offerlagbe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offerlagbe.Model.Story;
import com.example.offerlagbe.R;
import com.example.offerlagbe.databinding.StoryRvDesignBinding;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {

    ArrayList<Story> list;
    Context context;

    public StoryAdapter(ArrayList<Story> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Gathering photo from the design
        View view = LayoutInflater.from(context).inflate(R.layout.story_rv_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //hold image from the design //Set all views data
        Story story= list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        StoryRvDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StoryRvDesignBinding.bind(itemView);
        }
    }
}
