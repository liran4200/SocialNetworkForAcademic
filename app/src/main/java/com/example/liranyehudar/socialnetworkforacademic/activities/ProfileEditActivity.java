package com.example.liranyehudar.socialnetworkforacademic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.liranyehudar.socialnetworkforacademic.R;

public class ProfileEditActivity extends AppCompatActivity {

    final String USER_NAME = "name";
    final String USER_YEAR = "year";
    final String USER_COUNTRY = "country";
    final String USER_EDUCATION = "education";
    final String USER_CITY = "city";

    private EditText nameEdit;
    private EditText yearEdit;
    private EditText countryEdit;
    private EditText cityEdit;
    private EditText educationEdit;
    private Button saveButton;
    private String name;
    private String year;
    private String city;
    private String country;
    private String education;
    private boolean validName;
    private boolean validYear;
    private boolean validCountry;
    private boolean validCity;
    private boolean validEducation;
    private boolean isOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        init();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString().trim();
                country = countryEdit.getText().toString().trim();
                city = cityEdit.getText().toString().trim();
                education = educationEdit.getText().toString().trim();
                year = yearEdit.getText().toString().trim();

                validName = isValidName(name);
                validCountry = isValidName(country);
                validCity = isValidName(city);
                validEducation = isValidName(education);
                validYear = isValidYear(year);

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
                    startActivity(i);
                }

            }
        });

    }

    public boolean checkAll(){
        if(!validName && !validYear && !validEducation && !validCity && !validCountry){
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
        nameEdit = findViewById(R.id.edt_userName);
        countryEdit = findViewById(R.id.edt_country);
        cityEdit = findViewById(R.id.edt_city);
        educationEdit = findViewById(R.id.edt_education);
        yearEdit = findViewById(R.id.edt_year);
        saveButton = findViewById(R.id.btnSave);
    }

}
