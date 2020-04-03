package com.github.abdularis.trackmylocation.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AuditDate {
    @Id(autoincrement = true)
    private Long id;
    private String startDate;
    private String auditDate;
    private String endpointID;
    private String typeID;
    private String auditType;
    @Generated(hash = 11058534)
    public AuditDate(Long id, String startDate, String auditDate, String endpointID,
            String typeID, String auditType) {
        this.id = id;
        this.startDate = startDate;
        this.auditDate = auditDate;
        this.endpointID = endpointID;
        this.typeID = typeID;
        this.auditType = auditType;
    }
    @Generated(hash = 782124497)
    public AuditDate() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getAuditDate() {
        return this.auditDate;
    }
    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }
    public String getEndpointID() {
        return this.endpointID;
    }
    public void setEndpointID(String endpointID) {
        this.endpointID = endpointID;
    }
    public String getTypeID() {
        return this.typeID;
    }
    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }
    public String getAuditType() {
        return this.auditType;
    }
    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }
}
