package com.foribus.cityatoms.database.symptoms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.foribus.cityatoms.common.SyncService;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "symptoms_scores")
public class SymptomsScore {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NotNull
    @ColumnInfo(name = "throat")
    private String throat;

    @NotNull
    @ColumnInfo(name = "cough")
    private String cough;

    @NotNull
    @ColumnInfo(name = "fever")
    private String fever;

    @NotNull
    @ColumnInfo(name = "smell")
    private String smell;

    @NotNull
    @ColumnInfo(name = "breathing")
    private String breathing;

    @NotNull
    @ColumnInfo(name = "pneumonia")
    private String pneumonia;

    @NotNull
    @ColumnInfo(name = "c19")
    private String c19;

    @NotNull
    @ColumnInfo(name = "hospital")
    private String hospital;

    @NotNull
    @ColumnInfo(name = "score")
    private String score;

    @TypeConverters(SyncService.SyncStatusConverter.class)
    @ColumnInfo(name = "sync_status")
    private SyncService.SyncStatus syncStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThroat() {
        return throat;
    }

    public void setThroat(String throat) {
        this.throat = throat;
    }

    public String getCough() {
        return cough;
    }

    public void setCough(String cough) {
        this.cough = cough;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getPneumonia() {
        return pneumonia;
    }

    public void setPneumonia(String pneumonia) {
        this.pneumonia = pneumonia;
    }

    public String getC19() {
        return c19;
    }

    public void setC19(String c19) {
        this.c19 = c19;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public SyncService.SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncService.SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
}
