package com.foribus.cityatoms.database.symptoms;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SymptomsScoreDao {

    @Insert
    void insert(SymptomsScoreEntity symptomsScoreEntity);

    @Query("SELECT * FROM symptoms_scores ORDER BY id DESC LIMIT 1")
    SymptomsScoreEntity getLatestSymptomScoreInfo();

    @Query("SELECT * FROM symptoms_scores WHERE id=:id")
    SymptomsScoreEntity getSymptomScoreInfo(int id);
}
