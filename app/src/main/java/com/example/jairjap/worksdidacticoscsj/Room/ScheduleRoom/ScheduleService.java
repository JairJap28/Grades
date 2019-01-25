package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class ScheduleService {
    private static ScheduleDAO scheduleDAO;

    public void setScheduleDAO(ScheduleDAO scheduleDao) {
        scheduleDAO = scheduleDao;
    }

    public void insertSchedule(ScheduleModel scheduleModel){
        new InsertSchedule(scheduleDAO).execute(scheduleModel);
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
        public InsertSchedule(ScheduleDAO scheduleDao) {
            super(scheduleDao);
        }

        @Override
        protected Void doInBackground(ScheduleModel... scheduleModels) {
            scheduleDao.insertSchedule(scheduleModels[0]);
            return null;
        }
    }

}
