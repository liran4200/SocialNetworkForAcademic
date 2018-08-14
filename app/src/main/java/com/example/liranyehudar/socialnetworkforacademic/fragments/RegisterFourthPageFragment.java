package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.app.ProgressDialog;
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

import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.activities.MainActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.ProfileEditActivity;
import com.example.liranyehudar.socialnetworkforacademic.activities.RegistrationProccessActivity;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterCoursesSelection;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    private ProgressDialog progressDialog;
    private TextView selectCourse;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Student student;
    private String password,email;
    private int provider;
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
        password = (String) this.getArguments().get("password");
        email = (String) this.getArguments().get("email");

        if(email ==null && password== null) {
            provider = RegistrationTypes.BY_FACEBOOK;
        }
        else {provider = RegistrationTypes.BY_NEW_ACCOUNT;}

        bindUI();

        loadCourses();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setTitle("Finish");
                progressDialog.setMessage("Creating an account");
                progressDialog.show();
                Log.d("courses",selectedCourses.toString());
                setStudentCourses();
                if(provider == RegistrationTypes.BY_NEW_ACCOUNT){ // firebase provider
                    registerUser();
                }
                else { // facebook provider.
                    loadUserDataFromFacebook(AccessToken.getCurrentAccessToken());
                }
            }
        });

        return view;
    }

    private void setStudentCourses() {
        Map<String,Boolean> coursesId = new HashMap<>();
        for(Course c : selectedCourses) {
            coursesId.put(c.getKey(),true);
        }
        student.setCoursesId(coursesId);
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
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful() ) {
                    writeData();
                }
                else{
                    //handle error
                    Toast.makeText(getActivity().getApplicationContext(),"error", Toast.LENGTH_LONG);
                }
            }
        });
    }

    public void writeData() {
        database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        student.setKey(userId);
        DatabaseReference studentRef =  database.getReference().child("Students").child(userId);
        studentRef.setValue(student);

        DatabaseReference courseRef;
        // join student to courses selected.
        for(Course c : selectedCourses) {
            courseRef = database.getReference().child("Courses").child(c.getKey()).child("StudentsId");
            c.addStudentId(userId);
            courseRef.setValue(c.getStudentsId());
        }

        progressDialog.cancel();
        Toast.makeText(getActivity().getApplicationContext(),"sucess", Toast.LENGTH_LONG);
        Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
        intent.putExtra("source",RegistrationTypes.FR0M_REGISTRATION_PROCESS);
        intent.putExtra("student",student);
        startActivity(intent);
    }

    private void loadUserDataFromFacebook(AccessToken token) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                token, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        setStudentDetails(object);
                        writeData();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields","id,first_name,last_name,location.fields(location),email,picture.type(normal)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public void setStudentDetails(JSONObject object) {
        try {
            student.setFirstName(object.getString("first_name"));
            student.setLastName(object.getString("last_name"));
            student.setCity(object.getJSONObject("location").getJSONObject("location").getString("city"));
            student.setCountry(object.getJSONObject("location").getJSONObject("location").getString("country"));
            //    student.setProfileImageUrl(object.getJSONObject("picture")
            //           .getJSONObject("data").getString("url"));
        } catch (JSONException e) {
            Log.e("JsonError",e.getMessage());
        }
    }
}
