package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScheduleDAO {

    @Query("SELECT * FROM schedule")
    List<ScheduleModel> getSchedule();

    @Insert
    void insertSchedule(ScheduleModel scheduleModel);

    @Delete
    void deleteSubjectFromSchedule(ScheduleModel scheduleModel);

    @Query("DELETE FROM schedule WHERE id = :id")
    void delteById(int id);

}
