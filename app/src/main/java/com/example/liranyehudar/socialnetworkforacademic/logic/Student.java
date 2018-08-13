package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Student implements Serializable{

    private String key;
    private String firstName;
    private String lastName;
    private String field;
    private String academic;
    private String year;
    private Map<String,Boolean> CoursesId = new HashMap<>();
    //private String profileImageUrl;
    private String skills;
    private String country;
    private String city;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Student() {

    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Map<String, Boolean> getCoursesId() {
        return CoursesId;
    }


    public void setCoursesId(Map<String, Boolean> coursesId) {
        CoursesId = coursesId;
    }

    public Student(String key, String firstName, String lastName, String field, String academic, String year, String country, String city) {
        this.key = key;
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
