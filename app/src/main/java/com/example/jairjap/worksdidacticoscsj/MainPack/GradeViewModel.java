package com.example.jairjap.worksdidacticoscsj.MainPack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleModel;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class GradeViewModel extends AndroidViewModel {

    private SettingsDataBase settingsDB;
    private SettingsDao settingsDao;

    public GradeViewModel(@NonNull Application application) {
        super(application);

        settingsDB = SettingsDataBase.getInstance(new WeakReference<Context>(application));
        settingsDao = settingsDB.settingsDao();
    }

    LiveData<Boolean> getSchedule(){
        return settingsDao.getSchedule();
    }

    void insertSubject(SubjectModel subjectModel, ScheduleModel scheduleModel){

    }
}
