package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locker_pads")
public class LockerPad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String padCode;

    private String location;

    private int totalBays;

    private int availableBays;

    private double landingTariff;

    private boolean active;

    // Public no-argument constructor required by JPA
    public LockerPad() {
    }

    // Parameterized constructor
    public LockerPad(String padCode, String location, int totalBays, int availableBays, double landingTariff, boolean active) {
        this.padCode = padCode;
        this.location = location;
        this.totalBays = totalBays;
        this.availableBays = availableBays;
        this.landingTariff = landingTariff;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPadCode() {
        return padCode;
    }

    public void setPadCode(String padCode) {
        this.padCode = padCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalBays() {
        return totalBays;
    }

    public void setTotalBays(int totalBays) {
        this.totalBays = totalBays;
    }

    public int getAvailableBays() {
        return availableBays;
    }

    public void setAvailableBays(int availableBays) {
        this.availableBays = availableBays;
    }

    public double getLandingTariff() {
        return landingTariff;
    }

    public void setLandingTariff(double landingTariff) {
        this.landingTariff = landingTariff;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
