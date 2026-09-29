package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Payment reference is required")
    @Size(min = 1, max = 30, message = "Payment reference must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Payment reference can contain only letters, numbers, hyphens and underscores")
    private String paymentReference;

    @NotNull(message = "Invoice ID is required")
    @Positive(message = "Invoice ID must be a positive number")
    private Long invoiceId;

    @PositiveOrZero(message = "Amount must be 0 or greater")
    private double amount;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(BANK_TRANSFER|CASH|UPI)$", message = "Payment method must be BANK_TRANSFER, CASH or UPI")
    private String paymentMethod;

    @Pattern(regexp = "^(SUCCESS|PENDING|FAILED)$", message = "Status must be SUCCESS, PENDING or FAILED")
    private String status;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    // Public no-argument constructor required by JPA
    public Payment() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
