package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterCoursesGroups;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    private RecyclerView recyclerViewCourses;
    private RecycleViewAdpaterCoursesGroups adapter;
    private ArrayList<Course> coursesList;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_courses, container, false);

        bindUI(view);
        // Temp....
        coursesList = getCoursesList();
        adapter = new RecycleViewAdpaterCoursesGroups(coursesList,view.getContext());
        recyclerViewCourses.setAdapter(adapter);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    private void bindUI(View view) {
        recyclerViewCourses = view.findViewById(R.id.recycle_groups_courses);
    }

    public ArrayList<Course> getCoursesList() {
        ArrayList list = new ArrayList();
        return list;
    }

}
