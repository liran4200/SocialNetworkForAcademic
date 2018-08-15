package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterCoursesGroups;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesFragment extends Fragment {

    private RecyclerView recyclerViewCourses;
    private RecycleViewAdpaterCoursesGroups adapter;
    private ArrayList<Course> coursesList;

    private View view;
    private ProgressBar progressBar;
    private TextView txtNotRegister;
    private Button btnTryAgain;

    private DatabaseReference ref;
    private FirebaseDatabase database;

    public CoursesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_courses, container, false);
        coursesList = new ArrayList<>();
        bindUI();
        loadCourses();
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCourses();
            }
        });
        return view;
    }

    private void bindUI() {
        recyclerViewCourses = view.findViewById(R.id.recycle_groups_courses);
        progressBar = view.findViewById(R.id.prg_my_courses);
        txtNotRegister = view.findViewById(R.id.txt_no_register);
        btnTryAgain = view.findViewById(R.id.btn_try_again);
    }

    public void loadCourses() {
        final String userid = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Courses");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coursesList.clear();
                if(dataSnapshot!= null) {
                    addCourses(dataSnapshot, userid);
                    updateUI();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.getMessage()
                        ,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnTryAgain.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addCourses(DataSnapshot dataSnapshot,String userId){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Course c = ds.getValue(Course.class);
            if (c.isRegisteredStudent(userId))
                coursesList.add(c);
        }
    }

    private void updateUI() {
        progressBar.setVisibility(View.INVISIBLE);
        if(coursesList.size() == 0)
            txtNotRegister.setVisibility(View.VISIBLE);
        else {
            recyclerViewCourses.setVisibility(View.VISIBLE);
            adapter = new RecycleViewAdpaterCoursesGroups(coursesList, view.getContext());
            recyclerViewCourses.setAdapter(adapter);
            recyclerViewCourses.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }

}
