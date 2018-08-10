package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Map;

@IgnoreExtraProperties
public class Student implements Serializable{

    private String firstName;
    private String lastName;
    private String field;
    private String academic;
    private String year;
    private Map<String,Boolean> CoursesId;
    //private String profileImageUrl;
    //private String skills;
    private String country;
    private String city;

    public Student() {

    }

    public Map<String, Boolean> getCoursesId() {
        return CoursesId;
    }

    public void setCoursesId(Map<String, Boolean> coursesId) {
        CoursesId = coursesId;
    }

    public Student(String firstName, String lastName, String field, String academic, String year, String country, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.field = field;
        this.academic = academic;
        this.year = year;
        this.country = country;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public String toString() {
        return firstName+" "+lastName+" from" +city+","+country+" "+year+" "+field+" in "+academic;
    }
}
