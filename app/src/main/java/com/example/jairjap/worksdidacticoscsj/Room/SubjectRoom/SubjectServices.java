package com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubjectServices {
    private static SubjectDataBase db;

    public SubjectServices(WeakReference<Context> wContex) {
        db = SubjectDataBase.getInstance(wContex);
    }

    public LiveData<List<SubjectModel>> selectSubjects()
            throws ExecutionException, InterruptedException {
        return new SelectSubjectsAsync().execute().get();
    }

    public void insertSubject(SubjectModel subjectModel){
        new InsertSubjectAsync().execute(subjectModel);
    }

    public void updatePeriodGrade(SubjectModel subjectModel){
        new UpdatePeriodGradeAsync().execute(subjectModel);
    }

    public void updatePriority(String id, String priority){
        new UpdatePriorityAsync().execute(id, priority);
    }


    /* GETTER AND SETTERS */
    public static SubjectDataBase getDb() {
        return db;
    }

    static class SelectSubjectsAsync extends AsyncTask<Void, Void, LiveData<List<SubjectModel>>>{
        @Override
        protected LiveData<List<SubjectModel>> doInBackground(Void... voids){
            return db.subjectDAO().subjecs();
        }
    }

    static class InsertSubjectAsync extends AsyncTask<SubjectModel, Void, Void>{
        @Override
        protected Void doInBackground(SubjectModel... subjectModels) {
            db.subjectDAO().insertSubject(subjectModels);
            return null;
        }
    }

    static class UpdatePeriodGradeAsync extends AsyncTask<SubjectModel, Void, Void>{
        @Override
        protected Void doInBackground(SubjectModel... subjectModels) {
            db.subjectDAO().updatePeriodGrade(subjectModels[0]);
            return null;
        }
    }

    static class UpdatePriorityAsync extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            //pos 0 is the id
            //pos 1 is the new priority
            db.subjectDAO().updatePriority(strings[0], Float.parseFloat(strings[1]));
            return null;
        }
    }
}
