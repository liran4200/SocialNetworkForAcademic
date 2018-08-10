package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditActivity extends AppCompatActivity {

    final String USER_NAME = "name";
    final String USER_YEAR = "year";
    final String USER_COUNTRY = "country";
    final String USER_EDUCATION = "education";
    final String USER_CITY = "city";
    final String USER_SKILLS = "skills";
    final String USER_SKILLS_SIZE = "skillSize";
    final String STUDENT = "student";

    private EditText nameEdit;
    private EditText yearEdit;
    private EditText countryEdit;
    private EditText cityEdit;
    private EditText educationEdit;
    private EditText skillEdit;
    private Button saveButton;
    private String name;
    private String year;
    private String city;
    private String country;
    private String education;
    private String skills;
    private boolean validName;
    private boolean validYear;
    private boolean validCountry;
    private boolean validCity;
    private boolean validEducation;
    private boolean validSkills;
    private boolean isOk;
    private int skillSize;
    private String [] allSkills;
    private String [] fullName;
    public  Student student;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        // intent
        int source = 0 ;
        init();
        readFromDB();
        if(source == RegistrationTypes.FR0M_PROFILE) {
          //  updateUI();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString().trim();
                country = countryEdit.getText().toString().trim();
                city = cityEdit.getText().toString().trim();
                education = educationEdit.getText().toString().trim();
                year = yearEdit.getText().toString().trim();
                skills = skillEdit.getText().toString().trim();

                validName = isValidName(name);
                validCountry = isValidName(country);
                validCity = isValidName(city);
                validEducation = isValidName(education);
                validYear = isValidYear(year);
                validSkills = isValidSkills(skills);

                isOk = checkAll();

                if(isOk == false){
                    return;
                }else{
                    fullName = name.split(" ");
                    student.setFirstName(fullName[0]);
                    student.setLastName(fullName[1]);
                    student.setCountry(country);
                    student.setCity(city);
                    student.setAcademicInstitution(education);
                    student.setStudiesYear(year);
                   // student.setSkills(skills);
                    Intent i = new Intent(getBaseContext(),ProfileActivity.class);
                    i.putExtra(STUDENT,student);
                    i.putExtra(USER_SKILLS_SIZE,skillSize);

                    startActivity(i);
                }

            }
        });

    }

    private void readFromDB() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String userid = firebaseAuth.getUid();
        ref =  database.getReference("Students"+"/"+userid);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                Log.d("student",student.toString());
                updateUI(dataSnapshot);
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public boolean checkAll(){
        if(!validName && !validYear && !validEducation && !validCity && !validCountry&& !validSkills){
            showErrorMessage("You are not finish");
            return false;
        }

        if(!validName){
            showErrorMessage("Please enter your name" );
            return false;
        }

        if(!validYear){
            showErrorMessage("Please enter your year");
            return false;
        }
        if(!validCountry){
            showErrorMessage("Please enter your country");
            return false;
        }
        if(!validCity){
            showErrorMessage("Please enter your city");
            return false;
        }
        if(!validEducation){
            showErrorMessage("Please enter your education");
            return false;
        }
        if(!validSkills){
            showErrorMessage("Please enter your skills");
            return false;
        }else{
            return true;
        }
    }


    public void showErrorMessage(String message){
        AlertDialog.Builder theAlertDia  = new AlertDialog.Builder(ProfileEditActivity.this);
        theAlertDia.setMessage(message);
        theAlertDia.setTitle("Error Message");
        theAlertDia.setPositiveButton("OK", null);
        theAlertDia.setCancelable(false);
        theAlertDia.create().show();
    }

    public boolean isValidSkills(String skills){
        allSkills = skills.split(",");
        skillSize = allSkills.length;
        return true;
    }

    public boolean isValidName(String name){
        if(name.equals("")){
            return false;
        }
        return true;
    }

    public boolean isValidYear(String age){
        double  checkYear = 0;
        try{
            checkYear = Double.parseDouble(age);
        }
        catch (Exception e){
            return false;
        }
        return checkYear > 0 && checkYear <= 4;
    }

    public void init(){
        nameEdit = findViewById(R.id.edit_full_name);
        countryEdit = findViewById(R.id.edit_country);
        cityEdit = findViewById(R.id.edit_city);
        educationEdit = findViewById(R.id.edit_education);
        yearEdit = findViewById(R.id.edit_the_year);
        skillEdit = findViewById(R.id.edit_skill);
        saveButton = findViewById(R.id.btn_save);
    }

    public void updateUI(DataSnapshot ds) {
        try {
            String fullname = ds.child("firstName").getValue().toString() + ds.child("lastName").getValue();
            String city = ds.child("city").getValue().toString();
            String country = ds.child("country").getValue().toString();
            String academic = ds.child("academic").getValue().toString();
            String year = ds.child("year").getValue().toString();

//            nameEdit.setText(student.getFirstName()+" "+student.getLastName());
//            cityEdit.setText(student.getCity());
//            countryEdit.setText(student.getCountry());
//            educationEdit.setText(student.getAcademicInstitution() + ","+student.getFieldOfStudy());
//            yearEdit.setText(student.getStudiesYear());

            nameEdit.setText(fullname);
            cityEdit.setText(city);
            countryEdit.setText(country);
            educationEdit.setText(academic);
            yearEdit.setText(year);

        }
        catch (Exception e) {
            Log.e("err",e.getMessage());
        }
       // yearEdit.setText(ds.child("year").getValue(Student.class).getStudiesYear());
    }

}
