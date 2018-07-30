package com.example.liranyehudar.socialnetworkforacademic.logic;

import java.util.Objects;

public class Course {
    public enum Day {SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY;}
    public enum Semester {FIRST,SECOND,SUMMER;}

    public static int GEN_ID =1;

    private int id;
    private int courseNumber;

    private String courseName;
    private String lecture;

    private Semester semester;
    private Day day;
    private Time startTime;
    private Time endTime;

    public Course(int courseNumber, String courseName, String lecture,
                  Semester semester, Day day,Time startTime, Time endTime) {
        this.id = GEN_ID;
        this.GEN_ID++;
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.lecture = lecture;
        this.semester = semester;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                courseNumber == course.courseNumber;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, courseNumber);
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public boolean isOverLapping(Course c){
        if(!semester.name().equals(c.getSemester().name()))
            return false;

        if(!day.name().equals(c.getDay().name()))
            return false;

        if(c.getEndTime().compareTo(startTime)<=0)
            return false;

        if(c.getStartTime().compareTo(endTime)>=0)
            return false;

        return true;
    }

    public String heading() {
        return courseNumber+"-"+courseName;
    }

    public String description() {
        return semester.name()+" semester, "+lecture;
    }

    public String takesPlaceOn() {
        return startTime+"-"+endTime+", "+day.name();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseNumber=" + courseNumber +
                ", courseName='" + courseName + '\'' +
                ", lecture='" + lecture + '\'' +
                ", semester=" + semester +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
