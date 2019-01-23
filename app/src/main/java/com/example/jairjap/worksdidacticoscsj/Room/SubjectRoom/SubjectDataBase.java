package com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.jairjap.worksdidacticoscsj.Room.Converters;

import java.lang.ref.WeakReference;

@Database(entities = SubjectModel.class, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class SubjectDataBase extends RoomDatabase {
    public abstract SubjectDAO subjectDAO();
    private static SubjectDataBase instance;

    public static SubjectDataBase getInstance(WeakReference<Context> wContext){
        if(instance == null){
            instance = Room.databaseBuilder(wContext.get().getApplicationContext(),
                    SubjectDataBase.class, "subject_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
