package com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.util.SparseIntArray;

@Entity(tableName = "settings")
public class SettingsModel {
    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "periods_percentage")
    private SparseIntArray periods_percentage;

    @ColumnInfo(name = "max_grade")
    private float max_grade;

    @ColumnInfo(name = "max_credits")
    private int max_credits;

    @ColumnInfo(name = "schedule")
    private boolean create_scedule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SparseIntArray getPeriods_percentage() {
        return periods_percentage;
    }

    public void setPeriods_percentage(SparseIntArray periods_percentage) {
        this.periods_percentage = periods_percentage;
    }

    public float getMax_grade() {
        return max_grade;
    }

    public void setMax_grade(float max_grade) {
        this.max_grade = max_grade;
    }

    public int getMax_credits() {
        return max_credits;
    }

    public void setMax_credits(int max_credits) {
        this.max_credits = max_credits;
    }

    public boolean isCreate_scedule() {
        return create_scedule;
    }

    public void setCreate_scedule(boolean create_scedule) {
        this.create_scedule = create_scedule;
    }
}
