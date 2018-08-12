package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        init();
        setUI();
    }

    private void init(){
        courseName = (TextView)findViewById(R.id.course_the_name);
        courseNumnber = (TextView)findViewById(R.id.number_of_course);
        semester = (TextView)findViewById(R.id.num_of_semester);
        lecturer = (TextView)findViewById(R.id.lecturer);
        day = (TextView)findViewById(R.id.day_in_week);
        time = (TextView)findViewById(R.id.hours);
    }

    private void setUI(){

    }


}
