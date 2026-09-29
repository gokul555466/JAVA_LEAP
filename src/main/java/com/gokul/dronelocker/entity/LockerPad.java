package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "locker_pads")
public class LockerPad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Pad code is required")
    @Size(min = 1, max = 20, message = "Pad code must be between 1 and 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Pad code can contain only letters, numbers, hyphens and underscores")
    private String padCode;

    @NotBlank(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s,\\-]*$", message = "Location must start with a letter and contain only letters, numbers, spaces, commas and hyphens")
    private String location;

    @Min(value = 0, message = "Total bays must be 0 or greater")
    private int totalBays;

    @Min(value = 0, message = "Available bays must be 0 or greater")
    private int availableBays;

    @PositiveOrZero(message = "Landing tariff must be 0 or greater")
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
