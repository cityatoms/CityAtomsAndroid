package com.foribus.cityatoms.database.datapoints;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.foribus.cityatoms.common.SyncService;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(String epochTime) {
        this.epochTime = epochTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSymptomsScoreId() {
        return symptomsScoreId;
    }

    public void setSymptomsScoreId(int symptomsScoreId) {
        this.symptomsScoreId = symptomsScoreId;
    }

    public SyncService.SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncService.SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
}
