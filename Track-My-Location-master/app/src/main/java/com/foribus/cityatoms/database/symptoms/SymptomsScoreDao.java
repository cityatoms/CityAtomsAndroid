package com.foribus.cityatoms.database.symptoms;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SymptomsScoreDao {

    @Insert
    void insert(SymptomsScore symptomsScore);

    @Query("SELECT * FROM symptoms_scores WHERE sync_status = 0")
    List<SymptomsScore> getUnprocessedDataPoints();

    @Query("SELECT * FROM symptoms_scores WHERE sync_status = 1")
    List<SymptomsScore> getInProcessDataPoints();
}
