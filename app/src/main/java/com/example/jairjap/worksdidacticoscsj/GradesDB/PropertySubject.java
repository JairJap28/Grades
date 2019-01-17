package com.example.jairjap.worksdidacticoscsj.GradesDB;

import android.support.annotation.NonNull;

import com.example.jairjap.worksdidacticoscsj.Simulation.PropertyGrade;

public class PropertySubject implements Comparable<PropertySubject> {

    private long id;
    private String subject;
    private String teacher;
    private String credits;
    private int periods;
    private String grade;
    private String priority;
    private String finalGrade;

    public PropertySubject(String subject, String teacher, String credits) {
        this.subject = subject;
        this.teacher = teacher;
        this.credits = credits;
    }

    public PropertySubject(){
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grades) {
        this.grade = grades;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull PropertySubject o) {
        if(Float.parseFloat(this.getPriority()) > Float.parseFloat(o.getPriority())){
            return -1;
        }
        return 1;
    }


}
