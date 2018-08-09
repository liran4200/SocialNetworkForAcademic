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

    private FirebaseAuth firebaseAuth;
    private Student student;

    public RegisterFourthPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register_fourth_page, container, false);
        bindUI(view);
        firebaseAuth = FirebaseAuth.getInstance();
        student = (Student) this.getArguments().getSerializable("student");

        final HashSet<Course> selectedCourses = new HashSet<>();
        coursesList = getCoursesList();
        selectionAdapter = new RecycleViewAdapterCoursesSelection(coursesList,selectedCourses,view.getContext());
        recyclerViewSelectCourses.setAdapter(selectionAdapter);
        recyclerViewSelectCourses.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("courses",selectedCourses.toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    for (UserInfo profile : user.getProviderData()) {
                        // Id of the provider (ex: google.com)
                        String providerId = profile.getProviderId();
                        if(providerId.equals("facebook.com")){
                            Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                            intent.putExtra("student",student);
                            startActivity(intent);
                        }
                    }
                    //register user.
                    registerUser();
                }


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
