package com.foribus.cityatoms.Enitity;

import com.google.gson.annotations.SerializedName;

public class LoginEntity {

//    {
//        "is_confirmed": true,
//            "first_name": "hari",
//            "last_name": "gawkar",
//            "time_zone": "31",
//            "country_code": "IN",
//            "imei": "test123",
//            "id": "5e8f1c4650380e2786242e71"
//    }
    @SerializedName("is_confirmed")
    private boolean is_confirmed;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("time_zone")
    private String time_zone;

    @SerializedName("country_code")
    private String country_code;

    @SerializedName("imei")
    private String imei;

    @SerializedName("id")
    private String id;

    public boolean isIs_confirmed() {
        return is_confirmed;
    }

    public void setIs_confirmed(boolean is_confirmed) {
        this.is_confirmed = is_confirmed;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
