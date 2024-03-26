package com.example.offerlagbe.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.offerlagbe.Adapter.DashboardAdapter;
import com.example.offerlagbe.Adapter.StoryAdapter;
import com.example.offerlagbe.Model.DashboardModel;
import com.example.offerlagbe.Model.StoryModel;
import com.example.offerlagbe.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView storyRv,dashboardRv;
    ArrayList<StoryModel> list;
    ArrayList<DashboardModel> dashboardList;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        storyRv = view.findViewById(R.id.storyRV);
        list = new ArrayList<>();


        //Story,Story Type(live or normal),profile,string name
        list.add(new StoryModel(R.drawable.dennis,R.drawable.ic_video_camera,R.drawable.deaf,"Sulekho"));
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
        dashboardList = new ArrayList<>();

        dashboardList.add(new DashboardModel(R.drawable.profile,R.drawable.homefragmentstory02,R.drawable.savebookmark,
                "Sulekho","Clothing Brand","350","50","5"));
        dashboardList.add(new DashboardModel(R.drawable.profile,R.drawable.homefragmentstory,R.drawable.savebookmark,
                "Yellow","Clothing Brand","300","20","2"));
        dashboardList.add(new DashboardModel(R.drawable.profile,R.drawable.homefragmentstory02,R.drawable.savebookmark,
                "Sulekho","Clothing Brand","350","50","5"));
        dashboardList.add(new DashboardModel(R.drawable.profile,R.drawable.homefragmentstory02,R.drawable.savebookmark,
                "Sulekho","Clothing Brand","350","50","5"));
        dashboardList.add(new DashboardModel(R.drawable.profile,R.drawable.homefragmentstory02,R.drawable.savebookmark,
                "Sulekho","Clothing Brand","350","50","5"));


        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRv.setLayoutManager(layoutManager);
        dashboardRv.setNestedScrollingEnabled(false);
        dashboardRv.setAdapter(dashboardAdapter);
        return view;
    }
}