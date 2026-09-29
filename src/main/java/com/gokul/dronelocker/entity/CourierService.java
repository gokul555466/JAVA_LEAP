package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "courier_services")
public class CourierService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Service code is required")
    @Size(min = 1, max = 20, message = "Service code must be between 1 and 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Service code can contain only letters, numbers, hyphens and underscores")
    private String serviceCode;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z\\s'.\\-]*$", message = "Company name must start with a letter and contain only letters, spaces, apostrophes, dots and hyphens")
    private String companyName;

    @Size(max = 100, message = "Contact person name must not exceed 100 characters")
    @Pattern(regexp = "^$|^[A-Za-z][A-Za-z\\s'\\-]*$", message = "Contact person name must start with a letter and contain only letters, spaces, apostrophes and hyphens")
    private String contactPerson;

    @Email(message = "Enter a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Pattern(regexp = "^$|^[6-9][0-9]{9}$", message = "Enter a valid 10-digit Indian mobile number")
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    private boolean active;

    // Public no-argument constructor required by JPA
    public CourierService() {
    }

    // Parameterized constructor
    public CourierService(String serviceCode, String companyName, String contactPerson, String email, String phone, String address, boolean active) {
        this.serviceCode = serviceCode;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
