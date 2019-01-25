package com.example.jairjap.worksdidacticoscsj.MainPack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleDAO;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleModel;
import com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom.ScheduleService;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectServices;

import java.lang.ref.WeakReference;
import java.util.List;

public class GradeViewModel extends AndroidViewModel {

    //this to get settings
    private SettingsDao settingsDao;
    //this to save new subject
    private SubjectDAO subjectDao;
    //this to save schedule
    private ScheduleDAO scheduleDao;

    public GradeViewModel(@NonNull Application application) {
        super(application);

        SettingsDataBase settingsDB = SettingsDataBase.getInstance(new WeakReference<>(application));
        settingsDao = settingsDB.settingsDao();

        SubjectDataBase subjectDB = SubjectDataBase.getInstance(new WeakReference<>(application));
        subjectDao = subjectDB.subjectDAO();

        ScheduleDataBase scheduleDB = ScheduleDataBase.getInstance(new WeakReference<>(application));
        scheduleDao = scheduleDB.scheduleDAO();
    }

    LiveData<Boolean> getSchedule(){
        return settingsDao.getSchedule();
    }

    LiveData<Float> getMaxGrade(){ return settingsDao.getMaxGrade(); }

    LiveData<SparseIntArray> getPercentagePeriods() {return settingsDao.getPercentagePeriods();}

    void insertSubject(SubjectModel subjectModel, ScheduleModel scheduleModel){
        SubjectServices subjectServices = new SubjectServices(subjectDao);
        subjectServices.insertSubject(subjectModel);

        ScheduleService scheduleService = new ScheduleService();
        scheduleService.setScheduleDAO(scheduleDao);
        scheduleService.insertSchedule(scheduleModel);
    }
}
