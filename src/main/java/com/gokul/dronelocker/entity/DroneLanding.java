package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "drone_landings")
public class DroneLanding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String landingCode;

    private String droneCode;

    private String packageCode;

    private Long lockerPadId;

    private Long lockerBayId;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    private double landingFee;

    private String status;

    // Public no-argument constructor required by JPA
    public DroneLanding() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandingCode() {
        return landingCode;
    }

    public void setLandingCode(String landingCode) {
        this.landingCode = landingCode;
    }

    public String getDroneCode() {
        return droneCode;
    }

    public void setDroneCode(String droneCode) {
        this.droneCode = droneCode;
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

    public Long getLockerBayId() {
        return lockerBayId;
    }

    public void setLockerBayId(Long lockerBayId) {
        this.lockerBayId = lockerBayId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public double getLandingFee() {
        return landingFee;
    }

    public void setLandingFee(double landingFee) {
        this.landingFee = landingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
