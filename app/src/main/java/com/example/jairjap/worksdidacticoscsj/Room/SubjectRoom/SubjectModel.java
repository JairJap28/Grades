package com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;

@Entity(tableName = "Subjects")
public class SubjectModel implements Comparable<SubjectModel>{

   @ColumnInfo(name = "id")
   @PrimaryKey(autoGenerate = true)
   @NonNull
   private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "priority")
    private float priority;

    @ColumnInfo(name = "grade_needed")
    private float grade_needed;

    @ColumnInfo(name = "teacher_name")
    private String teacher_name;

    @ColumnInfo(name = "cant_perioids")
    private int cant_periods;

    @ColumnInfo(name = "cant_credits")
    private int cant_credits;

    @ColumnInfo(name = "peridos_Grade")
    private HashMap<String, Float> period_grade;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public float getGrade_needed() {
        return grade_needed;
    }

    public void setGrade_needed(float grade_needed) {
        this.grade_needed = grade_needed;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public int getCant_periods() {
        return cant_periods;
    }

    public void setCant_periods(int cant_periods) {
        this.cant_periods = cant_periods;
    }

    public int getCant_credits() {
        return cant_credits;
    }

    public void setCant_credits(int cant_credits) {
        this.cant_credits = cant_credits;
    }

    public HashMap<String, Float> getPeriod_grade() {
        return period_grade;
    }

    public void setPeriod_grade(HashMap<String, Float> period_grade) {
        this.period_grade = period_grade;
    }

    @Override
    public int compareTo(@NonNull SubjectModel o) {
        if(this.getPriority() > o.getPriority()){
            return -1;
        }
        return 1;
    }
}
