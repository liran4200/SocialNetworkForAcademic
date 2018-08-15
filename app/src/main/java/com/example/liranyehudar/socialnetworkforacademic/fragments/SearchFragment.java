package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.os.Bundle;
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
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.auth.FirebaseAuth;
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
    private ProgressBar progressBar;
    private TextView txtNoResult;

    private RecyclerView recyclerView;
    private RecycleViewAdapterSearching recycleViewAdapterSearching;
    private ArrayList<Student> students;

    private DatabaseReference ref;
    private FirebaseDatabase database;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_search, container, false);
        bindUI();
        init();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = edtSearch.getText().toString().trim();
                //handle nothing entered
                if(searchString.length()== 0){
                    Toast.makeText(view.getContext(),"Please write on search bar",Toast.LENGTH_SHORT).show();
                    return;
                }
                //init component visibility
                txtNoResult.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                loadStudents(searchString);
            }
        });
        return view;
    }
    private void init() {
        students = new ArrayList<>();
        recycleViewAdapterSearching= new RecycleViewAdapterSearching(view.getContext(),students);
        recyclerView.setAdapter(recycleViewAdapterSearching);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        radioStudentName.setActivated(true);
    }

    private void bindUI() {
        btnSearch = view.findViewById(R.id.button_search);
        edtSearch = view.findViewById(R.id.edt_search);
        radioStudentName = view.findViewById(R.id.rdio_student_name);
        recyclerView = view.findViewById(R.id.recycle_search);
        progressBar = view.findViewById(R.id.prg_loading_students);
        txtNoResult = view.findViewById(R.id.txt_no_found_result);
    }

    private void loadStudents(final String searchString) {
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Students");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                students.clear();
                if(dataSnapshot != null) {
                    addStudents(dataSnapshot, searchString);
                    updateUI();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(view.getContext(),databaseError.getMessage()
                        ,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addStudents(DataSnapshot dataSnapshot,String searchString) {
            String userId = FirebaseAuth.getInstance().getUid();
            for(DataSnapshot ds : dataSnapshot.getChildren()){
                Student s = ds.getValue(Student.class);
                if(!s.getKey().equals(userId)) { //not display the user who search.
                    String fullName = s.getFirstName() + " " + s.getLastName();
                    //ignore lowercase/uppercase
                    if (fullName.toLowerCase().contains(searchString.toLowerCase())) {
                        students.add(s);
                    }
                }
            }
    }

    private void updateUI() {
        progressBar.setVisibility(View.INVISIBLE);
        if(students.size() == 0){
            txtNoResult.setVisibility(View.VISIBLE);
        }
        else {
            recycleViewAdapterSearching.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

}
