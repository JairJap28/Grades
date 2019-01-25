package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.util.Date;
import java.util.HashMap;

@Entity(tableName = "schedule")
public class ScheduleModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "teacher_name")
    private String teacher_name;

    @ColumnInfo(name = "day_time")
    private SparseArray<String> day;

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

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public SparseArray<String> getDay() {
        return day;
    }

    public void setDay(SparseArray<String> day) {
        this.day = day;
    }
}
