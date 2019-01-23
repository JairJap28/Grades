package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private SubjectDAO subjectDao;
    private SubjectDataBase subjectDB;


    private LiveData<List<SubjectModel>> mAllSubjects;
    
    public SubjectViewModel(@NonNull Application application) {
        super(application);

        subjectDB = SubjectDataBase.getInstance(new WeakReference<Context>(application));
        subjectDao = subjectDB.subjectDAO();
        mAllSubjects = subjectDao.subjecs();
    }

    LiveData<List<SubjectModel>> getAllSubjects(){
        return mAllSubjects;
    }

}
