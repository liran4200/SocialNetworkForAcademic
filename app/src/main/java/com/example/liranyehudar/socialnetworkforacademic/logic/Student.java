package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Student implements Serializable{

    private String firstName;
    private String lastName;
    private String field;
    private String academic;
    private String year;
    //private String profileImageUrl;
    private String skills;
    private String country;
    private String city;

    public Student() {

    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
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

    public void setField(String fieldOfStudy) {
        this.field = fieldOfStudy;
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

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
