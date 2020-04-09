
package com.foribus.cityatoms.network.model;

import com.google.gson.annotations.SerializedName;

public class SymptomScores {

    @SerializedName("breathing")
    private Long mBreathing;
    @SerializedName("c19")
    private Long mC19;
    @SerializedName("cough")
    private Long mCough;
    @SerializedName("fever")
    private Long mFever;
    @SerializedName("hospital")
    private Long mHospital;
    @SerializedName("pneumonia")
    private Long mPneumonia;
    @SerializedName("score")
    private Long mScore;
    @SerializedName("smell")
    private Long mSmell;
    @SerializedName("throat")
    private Long mThroat;

    public Long getBreathing() {
        return mBreathing;
    }

    public void setBreathing(Long breathing) {
        mBreathing = breathing;
    }

    public Long getC19() {
        return mC19;
    }

    public void setC19(Long c19) {
        mC19 = c19;
    }

    public Long getCough() {
        return mCough;
    }

    public void setCough(Long cough) {
        mCough = cough;
    }

    public Long getFever() {
        return mFever;
    }

    public void setFever(Long fever) {
        mFever = fever;
    }

    public Long getHospital() {
        return mHospital;
    }

    public void setHospital(Long hospital) {
        mHospital = hospital;
    }

    public Long getPneumonia() {
        return mPneumonia;
    }

    public void setPneumonia(Long pneumonia) {
        mPneumonia = pneumonia;
    }

    public Long getScore() {
        return mScore;
    }

    public void setScore(Long score) {
        mScore = score;
    }

    public Long getSmell() {
        return mSmell;
    }

    public void setSmell(Long smell) {
        mSmell = smell;
    }

    public Long getThroat() {
        return mThroat;
    }

    public void setThroat(Long throat) {
        mThroat = throat;
    }

}
