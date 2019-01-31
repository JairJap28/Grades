package com.example.jairjap.worksdidacticoscsj.Subjects;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectServices;

import java.lang.ref.WeakReference;
import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private SubjectDAO subjectDao;

    //store all the subjects
    private LiveData<List<SubjectModel>> mAllSubjects;
    //store the period and its percentages
    private LiveData<SparseIntArray> periods_grade;
    //get the max grade
    private LiveData<Float> max_grade;
    //get the min grade
    private LiveData<Float> min_grade;
    
    public SubjectViewModel(@NonNull Application application) {
        super(application);

        SubjectDataBase subjectDB = SubjectDataBase.getInstance(new WeakReference<>(application));
        subjectDao = subjectDB.subjectDAO();
        mAllSubjects = subjectDao.subjecs();

        SettingsDataBase settingsDB = SettingsDataBase.getInstance(new WeakReference<>(application));
        //this is to get the periods and percentage
        SettingsDao settingsDao = settingsDB.settingsDao();

        periods_grade = settingsDao.getPercentagePeriods();
        max_grade = settingsDao.getMaxGrade();
        min_grade = settingsDao.getMinGrade();
    }

    LiveData<List<SubjectModel>> getAllSubjects(){
        return mAllSubjects;
    }

    LiveData<SparseIntArray> getPeriodPercentage() { return periods_grade; }

    LiveData<Float> getMaxGrade(){ return max_grade; }

    LiveData<Float> getMinGrade(){ return min_grade; }

    void updateSubjecs(List<SubjectModel> subjectModels){
        SubjectServices services = new SubjectServices(new WeakReference<>(getApplication()));
        services.updateSubject(subjectModels);
    }
}
