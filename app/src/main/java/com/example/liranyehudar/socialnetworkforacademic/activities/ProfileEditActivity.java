package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liranyehudar.socialnetworkforacademic.Interface.RegistrationTypes;
import com.example.liranyehudar.socialnetworkforacademic.R;

public class ProfileEditActivity extends AppCompatActivity {

    final String USER_NAME = "name";
    final String USER_YEAR = "year";
    final String USER_COUNTRY = "country";
    final String USER_EDUCATION = "education";
    final String USER_CITY = "city";
    final String USER_SKILLS = "skills";
    final String USER_SKILLS_SIZE = "skillSize";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


        // intent
        int source = 0 ;
        init();
        if(source == RegistrationTypes.FR0M_PROFILE) {
            updateUI();
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
                    Intent i = new Intent(getBaseContext(),ProfileActivity.class);
                    i.putExtra(USER_NAME,name);
                    i.putExtra(USER_COUNTRY,country);
                    i.putExtra(USER_CITY,city);
                    i.putExtra(USER_EDUCATION,education);
                    i.putExtra(USER_YEAR,year);
                    i.putExtra(USER_SKILLS,skills);

                    i.putExtra(USER_SKILLS_SIZE,skillSize);

                    startActivity(i);
                }

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

    private void updateUI() {
        nameEdit.setText(name);
        country = getIntent().getStringExtra(USER_COUNTRY).toString();
        countryEdit.setText(country);
        city = getIntent().getStringExtra(USER_CITY).toString();
        cityEdit.setText(city);
        education = getIntent().getStringExtra(USER_EDUCATION).toString();
        educationEdit.setText(education);
        year = getIntent().getStringExtra(USER_YEAR).toString();
        yearEdit.setText(year);
        skills = getIntent().getStringExtra(USER_YEAR).toString();
        skillEdit.setText(skills);

    }

}
