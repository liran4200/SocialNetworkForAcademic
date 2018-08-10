package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class Course {

    public enum Day {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY;}

    public enum Semester {FIRST, SECOND, SUMMER;}

    private int number;
    private String name;
    private String lecture;
    private Semester semester;
    private Day day;
    private Time startTime;
    private Time endTime;

    public Course() {

    }

    public Course(int number, String name, String lecture, Semester semester, Day day, Time startTime, Time endTime) {
        this.number = number;
        this.name = name;
        this.lecture = lecture;
        this.semester = semester;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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
    public int hashCode() {
        return Objects.hash(number);
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
        if (!semester.name().equals(c.getSemester().name()))
            return false;

        if (!day.name().equals(c.getDay().name()))
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
        return semester.name() + " semester, " + lecture;
    }

    public String takesPlaceOn() {
        return startTime + "-" + endTime + ", " + day.name();
    }

}

