package com.foribus.cityatoms.database.symptoms;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SymptomsScoreDao {

    @Insert
    void insert(SymptomsScoreEntity symptomsScoreEntity);

    @Query("SELECT * FROM symptoms_scores WHERE sync_status = 0")
    List<SymptomsScoreEntity> getUnprocessedDataPoints();

    @Query("SELECT * FROM symptoms_scores WHERE sync_status = 1")
    List<SymptomsScoreEntity> getInProcessDataPoints();

    @Query("SELECT * FROM symptoms_scores ORDER BY id DESC LIMIT 1")
    SymptomsScoreEntity getLatestSymptomScoreInfo();

    @Query("SELECT * FROM symptoms_scores WHERE id=:id")
    SymptomsScoreEntity getSymptomScoreInfo(int id);
}
