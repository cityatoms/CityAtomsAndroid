
package com.foribus.cityatoms.network.model;

import com.google.gson.annotations.SerializedName;

public class LocationSymptomRequest {

    @SerializedName("instance_id")
    private String mInstanceId;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lon")
    private Double mLon;
    @SerializedName("symptom_scores")
    private SymptomScores mSymptomScores;
    @SerializedName("time")
    private String mTime;
    @SerializedName("time_utc")
    private Long mTimeUtc;

    public String getInstanceId() {
        return mInstanceId;
    }

    public void setInstanceId(String instanceId) {
        mInstanceId = instanceId;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }

    public SymptomScores getSymptomScores() {
        return mSymptomScores;
    }

    public void setSymptomScores(SymptomScores symptomScores) {
        mSymptomScores = symptomScores;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public Long getTimeUtc() {
        return mTimeUtc;
    }

    public void setTimeUtc(Long timeUtc) {
        mTimeUtc = timeUtc;
    }

}
