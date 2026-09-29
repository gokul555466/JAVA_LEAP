package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "drone_landings")
public class DroneLanding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30, message = "Landing code must not exceed 30 characters")
    private String landingCode;

    @NotBlank(message = "Drone code is required")
    @Size(min = 1, max = 30, message = "Drone code must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Drone code can contain only letters, numbers, hyphens and underscores")
    private String droneCode;

    @NotBlank(message = "Package code is required")
    @Size(min = 1, max = 30, message = "Package code must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Package code can contain only letters, numbers, hyphens and underscores")
    private String packageCode;

    @NotNull(message = "Locker pad ID is required")
    @Positive(message = "Locker pad ID must be a positive number")
    private Long lockerPadId;

    private Long lockerBayId;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @PositiveOrZero(message = "Landing fee must be 0 or greater")
    private double landingFee;

    @Pattern(regexp = "^(LANDED|COMPLETED|CANCELLED)$", message = "Status must be LANDED, COMPLETED or CANCELLED")
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
