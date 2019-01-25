package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface ScheduleDAO {

    @Insert
    void insertSchedule(ScheduleModel scheduleModel);


}
