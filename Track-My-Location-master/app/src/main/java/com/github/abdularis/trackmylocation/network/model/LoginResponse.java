
package com.github.abdularis.trackmylocation.network.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;


@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("country_code")
    private String mCountryCode;
    @SerializedName("id")
    private String mId;
    @SerializedName("imei")
    private String mImei;
    @SerializedName("is_confirmed")
    private Boolean mIsConfirmed;
    @SerializedName("time_zone")
    private String mTimeZone;

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImei() {
        return mImei;
    }

    public void setImei(String imei) {
        mImei = imei;
    }

    public Boolean getIsConfirmed() {
        return mIsConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        mIsConfirmed = isConfirmed;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

}
