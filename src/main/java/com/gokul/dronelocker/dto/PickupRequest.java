package com.gokul.dronelocker.dto;

public class PickupRequest {

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
