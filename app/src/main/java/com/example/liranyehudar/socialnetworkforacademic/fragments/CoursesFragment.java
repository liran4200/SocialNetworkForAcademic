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
        list.add(new Course(10144,"Advance Alogrthim",
                "Dr.Dganit Armon",Course.Semester.FIRST,
                Course.Day.MONDAY, new Time("10","00"),new Time("12","00")));

        list.add(new Course(10122,"Data Structure",
                "Dr.Dganit Armon",Course.Semester.FIRST,
                Course.Day.SUNDAY,
                new Time("10","31"),new Time("12","00")));

        list.add(new Course(10143,"Assembly",
                "Mr.Adi Malach",Course.Semester.FIRST,
                Course.Day.MONDAY,
                new Time("10","32"),new Time("13","00")));

        list.add(new Course(10140,"Computers Communication",
                "Dr.Nir ...",Course.Semester.SECOND,
                Course.Day.MONDAY,
                new Time("10","00"),new Time("12","00")));

        list.add(new Course(90123,"Lgebra Linarit",
                "Dr.Hanna Clevner",Course.Semester.SUMMER,
                Course.Day.MONDAY,
                new Time("10","00"),new Time("12","00")));

        list.add(new Course(90111," Hedvar 1",
                "Dr.Alona muchov",Course.Semester.FIRST,
                Course.Day.MONDAY,
                new Time("09","00"),new Time("10","00")));

        list.add(new Course(90111," Hedvar 2",
                "Dr.Alona muchov",Course.Semester.FIRST,
                Course.Day.MONDAY,
                new Time("09","00"),new Time("11","30")));
        return list;
    }

}
