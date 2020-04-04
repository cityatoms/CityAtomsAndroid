package com.github.abdularis.trackmylocation.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LocationData {
    @Id(autoincrement = true)
    private Long id;
    private String hour;
    private String timestamp;
    private String latitude;
    private String longitude;
    @Generated(hash = 1612981149)
    public LocationData(Long id, String hour, String timestamp, String latitude,
            String longitude) {
        this.id = id;
        this.hour = hour;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Generated(hash = 1606831457)
    public LocationData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHour() {
        return this.hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
