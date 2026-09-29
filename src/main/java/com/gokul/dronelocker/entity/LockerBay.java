package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "locker_bays")
public class LockerBay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bay code is required")
    @Size(min = 1, max = 20, message = "Bay code must be between 1 and 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Bay code can contain only letters, numbers, hyphens and underscores")
    private String bayCode;

    @Pattern(regexp = "^(AVAILABLE|OCCUPIED)$", message = "Status must be either AVAILABLE or OCCUPIED")
    private String status;

    @Size(max = 30, message = "Package code must not exceed 30 characters")
    private String packageCode;

    @NotNull(message = "Locker pad ID is required")
    @Positive(message = "Locker pad ID must be a positive number")
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
