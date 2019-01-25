package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseIntArray;

import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private SubjectDAO subjectDao;
    //this is to get the periods and percentage
    private SettingsDao settingsDao;

    private LiveData<List<SubjectModel>> mAllSubjects;
    
    public SubjectViewModel(@NonNull Application application) {
        super(application);

        SubjectDataBase subjectDB = SubjectDataBase.getInstance(new WeakReference<>(application));
        subjectDao = subjectDB.subjectDAO();
        mAllSubjects = subjectDao.subjecs();

        SettingsDataBase settingsDB = SettingsDataBase.getInstance(new WeakReference<>(application));
        settingsDao = settingsDB.settingsDao();
    }

    LiveData<List<SubjectModel>> getAllSubjects(){
        return mAllSubjects;
    }
}
