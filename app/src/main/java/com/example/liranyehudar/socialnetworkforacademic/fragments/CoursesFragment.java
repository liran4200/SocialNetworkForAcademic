package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterCoursesSelection;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterCoursesGroups;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    private RecyclerView recyclerViewCourses;
    private RecycleViewAdpaterCoursesGroups adapter;
    private ArrayList<Course> coursesList;
    private DatabaseReference ref;
    private FirebaseDatabase database;
    private View view;
    private ProgressBar progressBar;
    private TextView txtNotRegister;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_courses, container, false);
        bindUI(view);
        loadCourses();
        return view;
    }

    private void bindUI(View view) {
        recyclerViewCourses = view.findViewById(R.id.recycle_groups_courses);
        progressBar = view.findViewById(R.id.prg_my_courses);
        txtNotRegister = view.findViewById(R.id.txt_no_register);
    }

    public void loadCourses() {
        final String userid = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Courses");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addCourses(dataSnapshot,userid);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addCourses(DataSnapshot dataSnapshot,String userId){
        coursesList = new ArrayList<>();
        try {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Course c = ds.getValue(Course.class);
                if (c.isRegisteredStudent(userId))
                    coursesList.add(c);
            }
        }catch (Exception e){
            Log.e("err",e.getMessage());
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
