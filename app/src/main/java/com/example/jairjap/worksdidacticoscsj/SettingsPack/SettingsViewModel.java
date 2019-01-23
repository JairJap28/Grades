package com.example.jairjap.worksdidacticoscsj.SettingsPack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDao;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsDataBase;
import com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom.SettingsModel;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDAO;
import com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom.SubjectDataBase;

import java.lang.ref.WeakReference;
import java.util.List;

public class SettingsViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private SettingsDataBase settingsDB;
    private SettingsDao settingsDao;

    private SettingsModel settingsModel;

    private LiveData<List<SettingsModel>> mAllsettings;

    public SettingsViewModel(@NonNull Application application) {
        super(application);

        settingsDB = SettingsDataBase.getInstance(new WeakReference<Context>(application));
        settingsDao = settingsDB.settingsDao();

        mAllsettings = settingsDao.settings();
    }

    LiveData<List<SettingsModel>> getSettings(){
        return mAllsettings;
    }

    void InsertSettings(SettingsModel settingsModel){
        SettingsServices.setSettingsDao(settingsDao);
        SettingsServices.insertSettings(settingsModel);
    }

    void setSettingsModel(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    @Override
    protected void onCleared() {
        if(settingsModel != null){
            SettingsServices.setSettingsDao(settingsDao);
            SettingsServices.updateSettings(settingsModel);
        }
        super.onCleared();
    }
}
