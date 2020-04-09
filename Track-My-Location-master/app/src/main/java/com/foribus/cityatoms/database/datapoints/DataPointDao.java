package com.foribus.cityatoms.database.datapoints;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataPointDao {

    @Insert
    void insert(DataPoint dataPoint);

    @Query("SELECT * FROM data_points WHERE sync_status = 0")
    List<DataPoint> getUnprocessedDataPoints();

    @Query("SELECT * FROM data_points WHERE sync_status = 1")
    List<DataPoint> getInProcessDataPoints();
}
