package com.foribus.cityatoms.database.symptoms;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "symptoms_scores")
public class SymptomsScoreEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NotNull
    @ColumnInfo(name = "throat", defaultValue = "0")
    private int throat;

    @NotNull
    @ColumnInfo(name = "cough", defaultValue = "0")
    private int cough;

    @NotNull
    @ColumnInfo(name = "fever", defaultValue = "0")
    private int fever;

    @NotNull
    @ColumnInfo(name = "smell", defaultValue = "0")
    private int smell;

    @NotNull
    @ColumnInfo(name = "breathing", defaultValue = "0")
    private int breathing;

    @NotNull
    @ColumnInfo(name = "pneumonia", defaultValue = "0")
    private int pneumonia;

    @NotNull
    @ColumnInfo(name = "c19", defaultValue = "0")
    private int c19;

    @NotNull
    @ColumnInfo(name = "hospital", defaultValue = "0")
    private int hospital;

    @NotNull
    @ColumnInfo(name = "score", defaultValue = "0")
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThroat() {
        return throat;
    }

    public void setThroat(int throat) {
        this.throat = throat;
    }

    public int getCough() {
        return cough;
    }

    public void setCough(int cough) {
        this.cough = cough;
    }

    public int getFever() {
        return fever;
    }

    public void setFever(int fever) {
        this.fever = fever;
    }

    public int getSmell() {
        return smell;
    }

    public void setSmell(int smell) {
        this.smell = smell;
    }

    public int getBreathing() {
        return breathing;
    }

    public void setBreathing(int breathing) {
        this.breathing = breathing;
    }

    public int getPneumonia() {
        return pneumonia;
    }

    public void setPneumonia(int pneumonia) {
        this.pneumonia = pneumonia;
    }

    public int getC19() {
        return c19;
    }

    public void setC19(int c19) {
        this.c19 = c19;
    }

    public int getHospital() {
        return hospital;
    }

    public void setHospital(int hospital) {
        this.hospital = hospital;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
