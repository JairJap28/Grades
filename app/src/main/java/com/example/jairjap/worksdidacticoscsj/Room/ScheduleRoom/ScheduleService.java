package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;

import java.lang.ref.WeakReference;

public class ScheduleService {
    private static ScheduleDAO scheduleDAO;

    public void setScheduleDAO(ScheduleDAO scheduleDao) {
        scheduleDAO = scheduleDao;
    }

    public void insertSchedule(ScheduleModel scheduleModel){
        new InsertSchedule(scheduleDAO).execute(scheduleModel);
    }

    public void deleteSubjectFromSchedule(ScheduleModel... scheduleModels){
        new DeleteSubjectFromSchedule(scheduleDAO).execute(scheduleModels);
    }

    public void delteById(Integer... ids){
        new DeleteById().execute(ids);
    }

    protected static class OperationAsync extends AsyncTask<ScheduleModel, Void, Void>{
        ScheduleDAO scheduleDao;

        OperationAsync(ScheduleDAO scheduleDao) {
            this.scheduleDao = scheduleDao;
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            return null;
        }
    }

    protected static class InsertSchedule extends OperationAsync{
        InsertSchedule(ScheduleDAO scheduleDao) {
            super(scheduleDao);
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            scheduleDao.insertSchedule(scheduleModels[0]);
            return null;
        }
    }

    protected static class DeleteSubjectFromSchedule extends OperationAsync{

        DeleteSubjectFromSchedule(ScheduleDAO scheduleDao) {
            super(scheduleDao);
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            for(ScheduleModel model: scheduleModels){
                scheduleDao.deleteSubjectFromSchedule(model);
            }
            return null;
        }
    }

    protected static class DeleteById extends AsyncTask<Integer, Voice, Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            for(int id: integers){
                scheduleDAO.delteById(id);
            }
            return null;
        }
    }

}
