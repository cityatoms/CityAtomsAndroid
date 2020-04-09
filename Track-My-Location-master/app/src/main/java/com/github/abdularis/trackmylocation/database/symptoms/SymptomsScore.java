package com.github.abdularis.trackmylocation.database.symptoms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.github.abdularis.trackmylocation.common.SyncService;

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
}
