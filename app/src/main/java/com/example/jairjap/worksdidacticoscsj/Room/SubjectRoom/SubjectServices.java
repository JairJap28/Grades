package com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubjectServices {
    private static SubjectDAO subjectDao;

    public SubjectServices(WeakReference<Context> wContext) {
        SubjectDataBase subjectDB = SubjectDataBase.getInstance(wContext);
        subjectDao = subjectDB.subjectDAO();
    }

    public SubjectServices(SubjectDAO subjectDao_in) {
        subjectDao = subjectDao_in;
    }

    public LiveData<List<SubjectModel>> selectSubjects()
            throws ExecutionException, InterruptedException {
        return new SelectSubjectsAsync().execute().get();
    }

    public void insertSubject(SubjectModel subjectModel){
        new InsertSubjectAsync().execute(subjectModel);
    }

    public void updateSubject(SubjectModel subjectModel){
        new UpdatePeriodGradeAsync().execute(subjectModel);
    }

    public void updatePeriodGrade(SparseArray<Float> period_grade, String in){

    }

    public void updatePriority(String id, String priority){
        new UpdatePriorityAsync().execute(id, priority);
    }


    /* GETTER AND SETTERS */
    static class SelectSubjectsAsync extends AsyncTask<Void, Void, LiveData<List<SubjectModel>>>{
        @Override
        protected LiveData<List<SubjectModel>> doInBackground(Void... voids){
            return subjectDao.subjecs();
        }
    }

    static class InsertSubjectAsync extends AsyncTask<SubjectModel, Void, Void>{
        @Override
        protected Void doInBackground(SubjectModel... subjectModels) {
            subjectDao.insertSubject(subjectModels);
            return null;
        }
    }

    static class UpdatePeriodGradeAsync extends AsyncTask<SubjectModel, Void, Void>{
        @Override
        protected Void doInBackground(SubjectModel... subjectModels) {
            subjectDao.updateSubject(subjectModels[0]);
            return null;
        }
    }

    static class UpdatePriorityAsync extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            //pos 0 is the id
            //pos 1 is the new priority
            subjectDao.updatePriority(strings[0], Float.parseFloat(strings[1]));
            return null;
        }
    }
}
