package com.example.jairjap.worksdidacticoscsj.Room.ScheduleRoom;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Pair;
import android.util.SparseArray;

import com.example.jairjap.worksdidacticoscsj.Widget.WidgetItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScheduleService {
    private static ScheduleDAO scheduleDAO;

    public ScheduleService(WeakReference<Context> wContext){
        if(scheduleDAO == null){
            scheduleDAO = ScheduleDataBase.getInstance(wContext).scheduleDAO();
        }
    }

    public ScheduleService() {
    }

    public void setScheduleDAO(ScheduleDAO scheduleDao) {
        scheduleDAO = scheduleDao;
    }

    public void insertSchedule(ScheduleModel scheduleModel){
        new InsertSchedule(scheduleDAO).execute(scheduleModel);
    }

    public List<WidgetItem> getDaySchedule(){
        List<ScheduleModel> scheduleModels;
        List<WidgetItem> out = new ArrayList<>();

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        int val_day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        try {
            scheduleModels = new GetScheduleDay(scheduleDAO).execute().get();
            for(ScheduleModel model: scheduleModels){
                HashMap<Integer,Pair<String, String>> day_time = model.getDay();

                WidgetItem item = new WidgetItem();
                item.setSubject(model.getName());
                item.setTeacher(model.getTeacher_name());
                item.setDay(val_day);

                if(day_time.containsKey(val_day)){
                    item.setClass_room(day_time.get(val_day).second);
                    item.setHour(day_time.get(val_day).first);
                }
                out.add(item);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return out;
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

    protected static class GetScheduleDay extends AsyncTask<String, Void, List<ScheduleModel>>{
        ScheduleDAO scheduleDAO;

        public GetScheduleDay(ScheduleDAO scheduleDAO) {
            this.scheduleDAO = scheduleDAO;
        }

        @Override
        protected List<ScheduleModel> doInBackground(String... strings) {
            return scheduleDAO.getSchedule();
        }
    }

}
