package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Course;
import com.example.liranyehudar.socialnetworkforacademic.logic.RecycleViewAdapterStudentInCourse;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    private Course course;
    private TextView courseName;
    private TextView courseNumnber;
    private TextView semester;
    private TextView lecturer;
    private TextView day;
    private TextView time;
    private TextView notification;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private RecycleViewAdapterStudentInCourse adapter;
    private ArrayList<Student>all_students;
    private String courseId;
    private ImageView imgBack;
    private Button selectFile;
    private Button upload;
    private Uri pdfOrDocxUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        init();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.super.onBackPressed();
                finish();
            }
        });
        courseId = getIntent().getStringExtra("courseId");
        readFromDBTheCourse();
        readFromDBTheParticipant();

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence [] items = {"upload docx file","upload pdf file","cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(items[which].equals("upload docx file")){
                            selectDoc();
                        }else if(items[which].equals("upload pdf file")){
                            selectPdf();
                        }else{
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfOrDocxUri != null){
                    uploadFile(pdfOrDocxUri);
                }else{
                    Toast.makeText(CourseActivity.this,"Please select a file",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadFile(final Uri pdfUri){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file....");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference();
        storageReference.child("course_files/"+course.getKey()+"/").child(fileName).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                DatabaseReference reference = database.getReference();
                Toast.makeText(CourseActivity.this,"File successfully upload",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CourseActivity.this,"File not successfully upload",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    private void selectPdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    private void selectDoc(){
        Intent intent = new Intent();
        intent.setType("application/docx");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 86 && resultCode == RESULT_OK && data != null){
            pdfOrDocxUri = data.getData();
            notification.setText("A file selected : " +data.getData().getLastPathSegment());
        }else{
            Toast.makeText(CourseActivity.this,"Please select a file",Toast.LENGTH_SHORT).show();
        }
    }

    private void init(){
        courseName = (TextView)findViewById(R.id.course_the_name);
        courseNumnber = (TextView)findViewById(R.id.number_of_course);
        semester = (TextView)findViewById(R.id.num_of_semester);
        lecturer = (TextView)findViewById(R.id.lecturer);
        day = (TextView)findViewById(R.id.day_in_week);
        time = (TextView)findViewById(R.id.hours);
        recyclerView = findViewById(R.id.recycle_view_student);
        imgBack = findViewById(R.id.imagView_back_home1);
        selectFile = findViewById(R.id.select_file);
        upload = findViewById(R.id.btn_files);
        notification = findViewById(R.id.the_files);
    }

    private void readFromDBTheCourse(){
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
        adapter = new RecycleViewAdapterStudentInCourse(all_students,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


}
