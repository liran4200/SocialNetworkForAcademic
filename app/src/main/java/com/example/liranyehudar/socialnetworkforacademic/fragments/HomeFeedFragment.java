package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileEditActivity;
import com.example.liranyehudar.socialnetworkforacademic.logic.ModelFeed;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterFeeds;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFeedFragment extends Fragment {

    CircleImageView profile;
    TextView txtPost;
    RecyclerView recyclerView;
    RecycleViewAdpaterFeeds adpaterFeeds;
    ArrayList<ModelFeed> modelFeedArrayList;

    public HomeFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_home_feed, container, false);

        bindUI(view);
        modelFeedArrayList = getData();

        adpaterFeeds = new RecycleViewAdpaterFeeds(modelFeedArrayList,view.getContext());
        recyclerView.setAdapter(adpaterFeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private ArrayList<ModelFeed> getData() {
        ArrayList<ModelFeed> list = new ArrayList<>();
        list.add(new ModelFeed(1,22,33,"Liran Yehudar",
                "Im Looking for my next challenger in web developer, please subscribe me","5 mins"));


        list.add(new ModelFeed(2,10,1,"NirFinz",
                "Im Kirchleh ya movrachhh, eize yuri ze ","8 mins"));

        list.add(new ModelFeed(3,13,5,"Yuri Vainshtein",
                "There is IOS DEVELOPER ???? , I need a help with my storage application.","1 hour"));

        list.add(new ModelFeed(4,100,99,"Dudu Kirchly",
                "Ma ze ze Tuna??, Tuna Tuna , Mborach mobrach bo lepo","13 hours"));
        return list;
    }

    private void bindUI(View view) {
        profile = view.findViewById(R.id.profile_image);
        recyclerView = view.findViewById(R.id.recycleView_posts);
        txtPost = view.findViewById(R.id.txt_post);
    }

}
