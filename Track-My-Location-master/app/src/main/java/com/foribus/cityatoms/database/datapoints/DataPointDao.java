package com.foribus.cityatoms.database.datapoints;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DataPointEntity dataPointEntity);

    @Update
    void update(List<DataPointEntity> entityList);

    @Update
    void update(DataPointEntity entity);

    @Query("DELETE FROM data_points")
    void wipeData();

    @Query("SELECT * FROM data_points WHERE sync_status = 0")
    List<DataPointEntity> getUnprocessedDataPoints();

    @Query("SELECT * FROM data_points WHERE sync_status = 1")
    List<DataPointEntity> getInProcessDataPoints();
}
