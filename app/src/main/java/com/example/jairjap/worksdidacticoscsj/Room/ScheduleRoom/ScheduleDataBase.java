package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.jairjap.worksdidacticoscsj.Room.Converters;

import java.lang.ref.WeakReference;

@Database(entities = ScheduleModel.class, version = 2, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class ScheduleDataBase extends RoomDatabase {
    public abstract ScheduleDAO scheduleDAO();

    private static ScheduleDataBase instance;

    public static ScheduleDataBase getInstance(WeakReference<Context> wContext){
        if(instance == null){
            instance = Room.databaseBuilder(wContext.get().getApplicationContext(),
                    ScheduleDataBase.class, "schedule_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
