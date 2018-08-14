package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterStudentInCourse;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdpaterCoursesGroups;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    private Course course;
    private TextView courseName;
    private TextView courseNumnber;
    private TextView semester;
    private TextView lecturer;
    private TextView day;
    private TextView time;
    private View view;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private RecycleViewAdapterStudentInCourse adapter;
    private ArrayList<Student>all_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        init();
        readFromDBTheCourse();
        readFromDBTheParticipant();
    }

    private void init(){
        courseName = (TextView)findViewById(R.id.course_the_name);
        courseNumnber = (TextView)findViewById(R.id.number_of_course);
        semester = (TextView)findViewById(R.id.num_of_semester);
        lecturer = (TextView)findViewById(R.id.lecturer);
        day = (TextView)findViewById(R.id.day_in_week);
        time = (TextView)findViewById(R.id.hours);
        recyclerView = findViewById(R.id.recycle_view_student);
    }

    private void readFromDBTheCourse(){
        final String courseId ="7kyxcrOhbONk8C2W9iKew7O41003";
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Courses/"+courseId);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                course = dataSnapshot.getValue(Course.class);
                setUI();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

    }

    private void readFromDBTheParticipant(){
        final String courseId ="7kyxcrOhbONk8C2W9iKew7O41003";
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference("Students");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                takeAllStudents(dataSnapshot,courseId);
                writeStudentToText();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    private void takeAllStudents(DataSnapshot dataSnapshot, String courseID){
        all_students = new ArrayList<>();
        try {
            for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                Student student = dataSn.getValue(Student.class);
                if (student.isRegisteredCourse(courseID)){
                    all_students.add(student);
                }

            }
        }catch (Exception e){
            Log.e("err",e.getMessage());
        }
    }


    private void setUI(){
        courseName.setText(course.getName());
        courseNumnber.setText("Course Number: "+course.getNumber());
        semester.setText("Semester: "+course.getSemester());
        lecturer.setText("Lecturer: "+course.getLecture());
        time.setText("Hours: "+course.getStartTime().toString() + "-"+course.getEndTime().toString());
        day.setText("Day: "+course.getDay());

    }

    private void writeStudentToText(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecycleViewAdapterStudentInCourse(all_students);
        recyclerView.setAdapter(adapter);

    }


}
