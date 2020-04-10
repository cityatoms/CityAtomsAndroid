package com.foribus.cityatoms.network.model;

import com.foribus.cityatoms.database.datapoints.DataPointEntity;
import com.foribus.cityatoms.database.symptoms.SymptomsScoreEntity;
import com.google.gson.annotations.SerializedName;

public class LocationSymptomRequest {

    @SerializedName("instance_id")
    private String mInstanceId;
    @SerializedName("lat")
    private double mLat;
    @SerializedName("lon")
    private double mLon;
    @SerializedName("symptom_scores")
    private SymptomScores mSymptomScores;
    @SerializedName("time")
    private String mTime;
    @SerializedName("time_utc")
    private long mTimeUtc;

    public static LocationSymptomRequest instance(DataPointEntity dataPointEntity, SymptomsScoreEntity symptomsScoreEntity) {
        if (dataPointEntity == null || symptomsScoreEntity == null)
            return null;

        LocationSymptomRequest request = new LocationSymptomRequest();
        request.setLat(dataPointEntity.getLat());
        request.setLon(dataPointEntity.getLon());
        request.setTime(dataPointEntity.getTime());
        request.setTimeUtc(dataPointEntity.getEpochTime());
        request.setSymptomScores(SymptomScores.instance(symptomsScoreEntity));

        return request;
    }

    public String getInstanceId() {
        return mInstanceId;
    }

    public void setInstanceId(String instanceId) {
        mInstanceId = instanceId;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double lon) {
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

    public long getTimeUtc() {
        return mTimeUtc;
    }

    public void setTimeUtc(long timeUtc) {
        mTimeUtc = timeUtc;
    }
}
