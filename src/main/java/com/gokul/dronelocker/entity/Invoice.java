package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Invoice number is required")
    @Size(min = 1, max = 30, message = "Invoice number must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Invoice number can contain only letters, numbers, hyphens and underscores")
    private String invoiceNumber;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z\\s'\\-]*$", message = "Customer name must start with a letter and contain only letters, spaces, apostrophes and hyphens")
    private String customerName;

    private Long courierServiceId;

    @Size(max = 30, message = "Package code must not exceed 30 characters")
    private String packageCode;

    @PositiveOrZero(message = "Landing fee must be 0 or greater")
    private double landingFee;

    @PositiveOrZero(message = "Storage fee must be 0 or greater")
    private double storageFee;

    @PositiveOrZero(message = "Total amount must be 0 or greater")
    private double totalAmount;

    @Pattern(regexp = "^$|^(UNPAID|PAID|CANCELLED)$", message = "Status must be UNPAID, PAID or CANCELLED")
    private String status;

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    // Public no-argument constructor required by JPA
    public Invoice() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCourierServiceId() {
        return courierServiceId;
    }

    public void setCourierServiceId(Long courierServiceId) {
        this.courierServiceId = courierServiceId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public double getLandingFee() {
        return landingFee;
    }

    public void setLandingFee(double landingFee) {
        this.landingFee = landingFee;
    }

    public double getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(double storageFee) {
        this.storageFee = storageFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
