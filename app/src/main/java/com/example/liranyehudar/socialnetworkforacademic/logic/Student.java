package com.example.liranyehudar.socialnetworkforacademic.logic;

import java.io.Serializable;

public class Student implements Serializable{

    private String id ;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String fieldOfStudy;
    private String academicInstitution;
    private String studiesYear;
    private String profileImageUrl;
    private String skills;
    private String country;
    private String city;

    public Student() {

    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getAcademicInstitution() {
        return academicInstitution;
    }

    public void setAcademicInstitution(String academicInstitution) {
        this.academicInstitution = academicInstitution;
    }

    public String getStudiesYear() {
        return studiesYear;
    }

    public void setStudiesYear(String studiesYear) {
        this.studiesYear = studiesYear;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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
