package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterSearching;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterCoursesGroups;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private View view;
    private ImageButton btnSearch;
    private EditText    edtSearch;
    private RadioButton radioStudentName;
    private ArrayList<Student> students;
    private ProgressBar progressBar;
    private TextView txtNoResult;

    private DatabaseReference ref;
    private FirebaseDatabase database;
    private RecyclerView recyclerView;
    private RecycleViewAdapterSearching recycleViewAdapterSearching;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        bindUI();
        students = new ArrayList<>();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = edtSearch.getText().toString();
                if(searchString.length()== 0){
                    Toast.makeText(view.getContext(),"Please write on search bar",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                loadStudents(searchString);
            }
        });
        return view;
    }

    private void bindUI() {
        btnSearch = view.findViewById(R.id.button_search);
        edtSearch = view.findViewById(R.id.edt_search);
        radioStudentName = view.findViewById(R.id.rdio_student_name);
        recyclerView = view.findViewById(R.id.recycle_search);
        progressBar = view.findViewById(R.id.prg_loading_courses);
        txtNoResult = view.findViewById(R.id.txt_)
    }

    private void loadStudents(final String searchString) {
        final String userId = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Students");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addStudents(dataSnapshot,searchString);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),"error to load",Toast.LENGTH_LONG);
            }
        });
    }

    private void addStudents(DataSnapshot dataSnapshot,String searchString) {
            for(DataSnapshot ds : dataSnapshot.getChildren()){
                Student s = ds.getValue(Student.class);
                String fullName = s.getFirstName()+" "+s.getLastName();
                if(fullName.toLowerCase().contains(searchString.toLowerCase())){
                    students.add(s);
                }
            }
    }

    private void updateUI() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recycleViewAdapterSearching= new RecycleViewAdapterSearching(view.getContext(),students);
        recyclerView.setAdapter(recycleViewAdapterSearching);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}
