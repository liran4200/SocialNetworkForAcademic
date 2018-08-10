package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.MainActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileEditActivity;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterCoursesSelection;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private View view;
    private ProgressBar progressBar;
    private TextView selectCourse;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Student student;
    private HashSet<Course> selectedCourses;
    public RegisterFourthPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_fourth_page, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        selectedCourses = new HashSet<>();
        coursesList = new ArrayList<>();
        student = (Student) this.getArguments().getSerializable("student");

        bindUI();
        loadCourses();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("courses",selectedCourses.toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                for (UserInfo profile : user.getProviderData()) {
                        // Id of the provider (ex: google.com)
                    String providerId = profile.getProviderId();
                    if(providerId.equals("facebook.com")){
                        Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                        intent.putExtra("student",student);
                        startActivity(intent);
                    }
                    //register user.
                    registerUser();
                }

            }
        });

        return view;
    }

    private void bindUI() {
        recyclerViewSelectCourses = view.findViewById(R.id.recycle_selction);
        btnSubmit = view.findViewById(R.id.btn_submit);
        progressBar = view.findViewById(R.id.prg_loading_courses);
        selectCourse = view.findViewById(R.id.txt_select_courses);
    }

    public void loadCourses() {
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Courses");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                addCourses(dataSnapshot);
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        progressBar.setVisibility(View.INVISIBLE);
        selectCourse.setVisibility(View.VISIBLE);
        recyclerViewSelectCourses.setVisibility(View.VISIBLE);
        selectionAdapter = new RecycleViewAdapterCoursesSelection(coursesList,selectedCourses,view.getContext());
        recyclerViewSelectCourses.setAdapter(selectionAdapter);
        recyclerViewSelectCourses.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void addCourses(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
             Course c = ds.getValue(Course.class);
             coursesList.add(c);
             Log.d("course",c.toString());
        }
    }

    public void registerUser() {
        firebaseAuth.createUserWithEmailAndPassword("aa@gmail.com","111111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful() ) {
                    Toast.makeText(getActivity().getApplicationContext(),"sucess", Toast.LENGTH_LONG);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("student",student);
                    startActivity(intent);
                }
                else{
                    //handle error
                    Toast.makeText(getActivity().getApplicationContext(),"error", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
