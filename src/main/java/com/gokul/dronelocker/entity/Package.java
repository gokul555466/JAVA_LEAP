package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageCode;

    private String recipientName;

    private String recipientPhone;

    private String recipientEmail;

    private String deliveryAddress;

    private String pickupPasscode;

    private String status;

    private Long courierServiceId;

    private Long lockerPadId;

    private Long lockerBayId;

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
