
package com.github.abdularis.trackmylocation.network.model;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginRequest {

    @SerializedName("country_code")
    private String mCountryCode;
    @SerializedName("instance_id")
    private String mInstanceId;
    @SerializedName("time_zone")
    private String mTimeZone;

    public String getCountryCode() {
        return mCountryCode;
    }

    public String getInstanceId() {
        return mInstanceId;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public static class Builder {

        private String mCountryCode;
        private String mInstanceId;
        private String mTimeZone;

        public LoginRequest.Builder withCountryCode(String countryCode) {
            mCountryCode = countryCode;
            return this;
        }

        public LoginRequest.Builder withInstanceId(String instanceId) {
            mInstanceId = instanceId;
            return this;
        }

        public LoginRequest.Builder withTimeZone(String timeZone) {
            mTimeZone = timeZone;
            return this;
        }

        public LoginRequest build() {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.mCountryCode = mCountryCode;
            loginRequest.mInstanceId = mInstanceId;
            loginRequest.mTimeZone = mTimeZone;
            return loginRequest;
        }

    }

}
