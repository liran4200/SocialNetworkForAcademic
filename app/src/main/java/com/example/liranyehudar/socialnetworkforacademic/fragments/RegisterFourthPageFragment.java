package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterCoursesSelection;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFourthPageFragment extends Fragment {

    private RecyclerView recyclerViewSelectCourses;
    private ArrayList<Course> coursesList;
    private RecycleViewAdapterCoursesSelection selectionAdapter;
    private Button btnSubmit;

    public RegisterFourthPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_fourth_page, container, false);
        bindUI(view);
        final HashSet<Course> selectedCourses = new HashSet<>();
        coursesList = getCoursesList();
        selectionAdapter = new RecycleViewAdapterCoursesSelection(coursesList,selectedCourses,view.getContext());
        recyclerViewSelectCourses.setAdapter(selectionAdapter);
        recyclerViewSelectCourses.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("courses",selectedCourses.toString());
                // go to feed page.
            }
        });

        return view;
    }

    private void bindUI(View view) {
        recyclerViewSelectCourses = view.findViewById(R.id.recycle_selction);
        btnSubmit = view.findViewById(R.id.btn_submit);
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
