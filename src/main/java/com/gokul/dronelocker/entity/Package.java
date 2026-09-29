package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Package code is required")
    @Size(min = 1, max = 30, message = "Package code must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Package code can contain only letters, numbers, hyphens and underscores")
    private String packageCode;

    @NotBlank(message = "Recipient name is required")
    @Size(min = 2, max = 100, message = "Recipient name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z\\s'\\-]*$", message = "Recipient name must start with a letter and contain only letters, spaces, apostrophes and hyphens")
    private String recipientName;

    @Pattern(regexp = "^$|^[6-9][0-9]{9}$", message = "Enter a valid 10-digit Indian mobile number")
    private String recipientPhone;

    @Email(message = "Enter a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String recipientEmail;

    @Size(max = 255, message = "Delivery address must not exceed 255 characters")
    private String deliveryAddress;

    @Size(max = 10, message = "Pickup passcode must not exceed 10 characters")
    private String pickupPasscode;

    @Pattern(regexp = "^$|^(REGISTERED|IN_TRANSIT|DELIVERED_TO_LOCKER|PICKED_UP|CANCELLED)$", message = "Status must be REGISTERED, IN_TRANSIT, DELIVERED_TO_LOCKER, PICKED_UP or CANCELLED")
    private String status;

    private Long courierServiceId;

    private Long lockerPadId;

    private Long lockerBayId;

    @PositiveOrZero(message = "Storage fee must be 0 or greater")
    private double storageFee;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    @Column(name = "picked_up_at")
    private LocalDateTime pickedUpAt;

    // Public no-argument constructor required by JPA
    public Package() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPickupPasscode() {
        return pickupPasscode;
    }

    public void setPickupPasscode(String pickupPasscode) {
        this.pickupPasscode = pickupPasscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCourierServiceId() {
        return courierServiceId;
    }

    public void setCourierServiceId(Long courierServiceId) {
        this.courierServiceId = courierServiceId;
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

    public double getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(double storageFee) {
        this.storageFee = storageFee;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getPickedUpAt() {
        return pickedUpAt;
    }

    public void setPickedUpAt(LocalDateTime pickedUpAt) {
        this.pickedUpAt = pickedUpAt;
    }
}
