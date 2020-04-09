package com.github.abdularis.trackmylocation.database.datapoints;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.github.abdularis.trackmylocation.common.SyncService;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "data_points",
        indices = {
                @Index(value = {"time_utc"}, unique = true)
        })
public class DataPoint {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NotNull
    @ColumnInfo(name = "lat")
    private String lat;

    @NotNull
    @ColumnInfo(name = "lon")
    private String lon;

    @NotNull
    @ColumnInfo(name = "time_utc")
    private String epochTime;

    @NotNull
    @ColumnInfo(name = "time")
    private String time;

    @NotNull
    @ColumnInfo(name = "symptoms_score_id")
    private int symptomsScoreId;

    @TypeConverters(SyncService.SyncStatusConverter.class)
    @ColumnInfo(name = "sync_status")
    private SyncService.SyncStatus syncStatus;
}
