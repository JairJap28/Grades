package com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.jairjap.worksdidacticoscsj.Room.Converters;

import java.lang.ref.WeakReference;

@Database(entities = SettingsModel.class, version = 4 , exportSchema = false)
@TypeConverters(Converters.class)
public abstract class SettingsDataBase extends RoomDatabase {

    public abstract SettingsDao settingsDao();

    private static SettingsDataBase instance;

    public static SettingsDataBase getInstance(WeakReference<Context> wContext){
        if(instance == null){
            instance = Room.databaseBuilder(wContext.get().getApplicationContext(),
                    SettingsDataBase.class, "settings_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
