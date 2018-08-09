package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.Time;

public class CourseActivity extends AppCompatActivity {

    private Course course;
    private TextView courseName;
    private TextView courseNumnber;
    private TextView semester;
    private TextView lecturer;
    private TextView day;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        course = new Course(10143,"Assembly", "Mr.Adi Malach",Course.Semester.FIRST, Course.Day.MONDAY, new Time("10","32"),new Time("13","00"));
        init();
        setUI();
    }

    private void init(){
        courseName = findViewById(R.id.course_the_name);
        courseNumnber =findViewById(R.id.number_of_course);
        semester = findViewById(R.id.num_of_semester);
        lecturer = findViewById(R.id.lecturer);
        day = findViewById(R.id.day_in_week);
        time = findViewById(R.id.hours);
    }

    private void setUI(){
       courseName.setText(course.getCourseName());
       courseNumnber.setText(String.valueOf(course.getCourseNumber()));
       semester.setText(course.getSemester().name());
       lecturer.setText(course.getLecture());
       day.setText(course.getDay().name());
       time.setText(course.getStartTime().toString() + "-" +course.getEndTime().toString());
    }


}
