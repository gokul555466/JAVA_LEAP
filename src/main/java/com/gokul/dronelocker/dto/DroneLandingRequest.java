package com.gokul.dronelocker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DroneLandingRequest {

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

    public DroneLandingRequest() {
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
}
