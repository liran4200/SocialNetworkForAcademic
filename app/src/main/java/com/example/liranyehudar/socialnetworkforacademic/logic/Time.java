package com.example.liranyehudar.socialnetworkforacademic.logic;

import android.support.annotation.NonNull;

import java.util.Objects;

public class Time implements Comparable<Time> {

    private String hour;
    private String minute;

    public Time(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return hour.equals(time.hour) &&
                minute.equals(time.minute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        return hour+":"+minute;
    }

    @Override
    public int compareTo(Time t) {
        int hourT = Integer.parseInt(t.getHour());
        int minT = Integer.parseInt(t.getMinute());

        int hour = Integer.parseInt(this.hour);
        int min = Integer.parseInt(this.minute);

        if(hour > hourT)
            return 1;

        if(hour < hourT)
            return -1;

        if(min> minT)
            return 1;

        if(min< minT)
            return -1;

        return 0;
    }
}
