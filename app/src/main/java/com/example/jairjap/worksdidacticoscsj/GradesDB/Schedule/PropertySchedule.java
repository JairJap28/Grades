package com.example.jairjap.worksdidacticoscsj.GradesDB.Schedule;

import android.support.annotation.NonNull;

import com.example.jairjap.worksdidacticoscsj.Simulation.PropertyGrade;

public class PropertySchedule implements Comparable<PropertySchedule>{
    private String subject;
    private String hour;
    private int index;

    public PropertySchedule(String subject, int index){
        this.subject = subject;
        this.index = index;
        this.hour = "00:00";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public int compareTo(@NonNull PropertySchedule o) {
        return this.index > o.index ? 1 : -1;
    }
}
