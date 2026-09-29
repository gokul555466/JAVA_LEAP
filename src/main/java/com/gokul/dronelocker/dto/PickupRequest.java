package com.gokul.dronelocker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PickupRequest {

    @NotBlank(message = "Passcode is required")
    @Size(min = 4, max = 10, message = "Passcode must be between 4 and 10 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Passcode must contain only digits")
    private String passcode;

    // Public no-argument constructor
    public PickupRequest() {
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
