package com.example.jairjap.worksdidacticoscsj.Room.SubjectRoom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.SparseArray;

import java.util.List;

@Dao
public interface SubjectDAO {

    @Query("SELECT * FROM subjects")
    LiveData<List<SubjectModel>> subjecs();

    @Insert
    void insertSubject(SubjectModel... subjectModel);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateSubject(SubjectModel subjectModel);

    @Query("UPDATE subjects SET peridos_Grade = :periods_grade WHERE id = :id")
    void updatePeriodGrade(SparseArray<Float> periods_grade, int id);

    @Query("UPDATE subjects SET priority = :priority WHERE id = :id")
    void updatePriority(String id, float priority);
}

