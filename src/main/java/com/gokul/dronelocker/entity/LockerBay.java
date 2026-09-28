package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locker_bays")
public class LockerBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bayCode;

    private String status;

    private String packageCode;

    private Long lockerPadId;

    // Public no-argument constructor required by JPA
    public LockerBay() {
    }

    // Parameterized constructor
    public LockerBay(String bayCode, String status, String packageCode, Long lockerPadId) {
        this.bayCode = bayCode;
        this.status = status;
        this.packageCode = packageCode;
        this.lockerPadId = lockerPadId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBayCode() {
        return bayCode;
    }

    public void setBayCode(String bayCode) {
        this.bayCode = bayCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Long getLockerPadId() {
        return lockerPadId;
    }

    public void setLockerPadId(Long lockerPadId) {
        this.lockerPadId = lockerPadId;
    }
}
