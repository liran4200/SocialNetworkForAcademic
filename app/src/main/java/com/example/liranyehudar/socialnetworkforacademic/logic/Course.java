package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@IgnoreExtraProperties
public class Course {

    private String key;
    private String number;
    private String name;
    private String lecture;
    private String semester;
    private String day;
    private Time startTime;
    private Time endTime;
    private Map<String,Boolean> StudentsId = new HashMap<>();
    private int uploadFiles = 0;
    private String namesOfFile;

    public Course() {

    }

    public void setNamesOfFile(String nameFile) {
        if(this.namesOfFile == null){
            this.namesOfFile = nameFile +",";
        }else{
            this.namesOfFile += nameFile +"," ;
        }
    }

    public String getNamesOfFile() {
        return namesOfFile;
    }

    public void setUploadFiles(int uploadFiles) {
        this.uploadFiles += uploadFiles ;
    }

    public int getUploadFiles() {
        return uploadFiles;
    }


    public Map<String, Boolean> getStudentsId() {
        return StudentsId;
    }

    public void setStudentsId(Map<String, Boolean> studentsId) {
        StudentsId = studentsId;
    }

    public boolean isRegisteredStudent(String studentId) {
        return StudentsId.containsKey(studentId);
    }

    public void addStudentId(String id) {
        StudentsId.put(id,true);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key,number);
    }

    public Course(String key, String number, String name, String lecture, String semester, String day, Time startTime, Time endTime) {
        this.key = key;
        this.number = number;
        this.name = name;
        this.lecture = lecture;
        this.semester = semester;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getNumber() {
        return number;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }


    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return number == course.number;
    }


    @Override
    public String toString() {
        return "Course{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", lecture='" + lecture + '\'' +
                ", semester=" + semester +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public boolean isOverLapping(Course c) {
        if (!semester.equals(c.getSemester()))
            return false;

        if (!day.equals(c.getDay()))
            return false;

        if (c.getEndTime().compareTo(startTime) <= 0)
            return false;

        if (c.getStartTime().compareTo(endTime) >= 0)
            return false;

        return true;
    }

    public String heading() {
        return number + "-" + name;
    }

    public String description() {
        return semester + " semester, " + lecture;
    }

    public String takesPlaceOn() {
        return startTime + "-" + endTime + ", " + day;
    }

}

