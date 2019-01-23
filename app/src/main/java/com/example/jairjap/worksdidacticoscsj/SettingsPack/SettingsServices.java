package com.example.jairjap.worksdidacticoscsj.SettingsPack;

import android.os.AsyncTask;

import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectModel;

class SettingsServices {

    private static SettingsDao settingsDao;

    static void setSettingsDao(SettingsDao settingsDaoIn){
        settingsDao = settingsDaoIn;
    }

    public static void updateSettings(SettingsModel settingsModel){
        new UpdateSetting(settingsDao).execute(settingsModel);
    }

    public static void insertSettings(SettingsModel settingsModel){
        new InsertSetting(settingsDao).execute(settingsModel);
    }

    protected static class OperationAsyncTask extends AsyncTask<SettingsModel, Void, Void>{

        SettingsDao mAsyncSetingsDAO;

        OperationAsyncTask(SettingsDao settingsDao) {
            this.mAsyncSetingsDAO = settingsDao;
        }

        @Override
        protected Void doInBackground(SettingsModel... settingsModels) {
            return null;
        }
    }

    protected static class InsertSetting extends OperationAsyncTask{

        InsertSetting(SettingsDao settingsDao) {
            super(settingsDao);
        }

        @Override
        protected Void doInBackground(SettingsModel... settingsModels) {
            mAsyncSetingsDAO.insertSettings(settingsModels[0]);
            return super.doInBackground(settingsModels);
        }
    }

    protected static class UpdateSetting extends OperationAsyncTask{

        UpdateSetting(SettingsDao settingsDao) {
            super(settingsDao);
        }

        @Override
        protected Void doInBackground(SettingsModel... settingsModels) {
            mAsyncSetingsDAO.updateSetting(settingsModels[0]);
            return null;
        }
    }
}
