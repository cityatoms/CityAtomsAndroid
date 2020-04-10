package com.foribus.cityatoms.network.model;

import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SymptomScores {

    @SerializedName("breathing")
    private int mBreathing;
    @SerializedName("c19")
    private int mC19;
    @SerializedName("cough")
    private int mCough;
    @SerializedName("fever")
    private int mFever;
    @SerializedName("hospital")
    private int mHospital;
    @SerializedName("pneumonia")
    private int mPneumonia;
    @SerializedName("score")
    private int mScore;
    @SerializedName("smell")
    private int mSmell;
    @SerializedName("throat")
    private int mThroat;

    public static SymptomScores NORMAL() {
        SymptomScores symptomScores = new SymptomScores();
        return symptomScores;
    }

    public static SymptomScores HOSPITAL() {
        SymptomScores symptomScores = new SymptomScores();
        symptomScores.setHospital(1);
        return symptomScores;
    }

    public static SymptomScores C19() {
        SymptomScores symptomScores = new SymptomScores();
        symptomScores.setC19(1);
        return symptomScores;
    }

    public static SymptomScores instance(SymptomsScoreEntity entity) {
        if (entity == null)
            return null;

        SymptomScores symptomScores = new SymptomScores();
        symptomScores.setBreathing(entity.getBreathing());
        symptomScores.setC19(entity.getC19());
        symptomScores.setCough(entity.getCough());
        symptomScores.setFever(entity.getFever());
        symptomScores.setHospital(entity.getHospital());
        symptomScores.setPneumonia(entity.getPneumonia());
        symptomScores.setScore(entity.getScore());
        symptomScores.setSmell(entity.getSmell());
        symptomScores.setThroat(entity.getThroat());

        return symptomScores;
    }

    public SymptomsScoreEntity toEntity() {
        SymptomsScoreEntity entity = new SymptomsScoreEntity();
        entity.setBreathing(getBreathing());
        entity.setC19(getC19());
        entity.setCough(getCough());
        entity.setFever(getFever());
        entity.setHospital(getHospital());
        entity.setPneumonia(getPneumonia());
        entity.setScore(getScore());
        entity.setSmell(getSmell());
        entity.setThroat(getThroat());
        entity.setCreatedOn((new Date()).getTime());

        return entity;
    }

    public int getBreathing() {
        return mBreathing;
    }

    public void setBreathing(int breathing) {
        mBreathing = breathing;
    }

    public int getC19() {
        return mC19;
    }

    public void setC19(int c19) {
        mC19 = c19;
    }

    public int getCough() {
        return mCough;
    }

    public void setCough(int cough) {
        mCough = cough;
    }

    public int getFever() {
        return mFever;
    }

    public void setFever(int fever) {
        mFever = fever;
    }

    public int getHospital() {
        return mHospital;
    }

    public void setHospital(int hospital) {
        mHospital = hospital;
    }

    public int getPneumonia() {
        return mPneumonia;
    }

    public void setPneumonia(int pneumonia) {
        mPneumonia = pneumonia;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getSmell() {
        return mSmell;
    }

    public void setSmell(int smell) {
        mSmell = smell;
    }

    public int getThroat() {
        return mThroat;
    }

    public void setThroat(int throat) {
        mThroat = throat;
    }
}
