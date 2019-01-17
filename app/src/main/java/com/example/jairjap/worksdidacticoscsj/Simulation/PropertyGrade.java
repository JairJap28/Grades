package com.example.jairjap.worksdidacticoscsj.Simulation;

public class PropertyGrade {

    private String name;
    private String grade;
    private String credits;

    public PropertyGrade(String name, String grade){
        this.name = name;
        this.grade = grade;
        this.credits = "0";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }
}
