package com.example.jairjap.worksdidacticoscsj.Room.SettingsRoom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings")
    LiveData<List<SettingsModel>> settings();

    @Query("SELECT schedule FROM settings WHERE id = 'setting_1' ")
    LiveData<Boolean> getSchedule();

    @Query("SELECT max_grade FROM settings WHERE id = 'setting_1' ")
    LiveData<Float> getMaxGrade();

    @Query("SELECT periods_percentage FROM settings WHERE id = 'setting_1'")
    LiveData<SparseIntArray> getPercentagePeriods();

    @Insert
    void insertSettings(SettingsModel settingsModel);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateSetting(SettingsModel settingsModel);

    @Query("UPDATE settings SET max_grade = :max_grade WHERE id = :id")
    void updateMaxGrade(String id, Float max_grade);

    @Query("UPDATE settings SET max_credits = :max_credits WHERE id = :id")
    void updateMaxCredits(String id, int max_credits);

    @Query("UPDATE settings SET schedule = :check WHERE id = :id")
    void updateCreateSchedule(String id, boolean check);

    @Query("Update settings SET periods_percentage = :percentage_periods WHERE id = :id")
    void updatePeriodPercentage(String id, SparseIntArray percentage_periods);
}
