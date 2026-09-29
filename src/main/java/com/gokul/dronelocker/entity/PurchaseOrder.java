package com.gokul.dronelocker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Purchase order number is required")
    @Size(min = 1, max = 30, message = "Purchase order number must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Purchase order number can contain only letters, numbers, hyphens and underscores")
    private String purchaseOrderNumber;

    @Positive(message = "Vendor ID must be a positive number")
    private Long vendorId;

    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 100, message = "Item name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s,'.\\-]*$", message = "Item name must start with a letter and contain only letters, numbers, spaces, commas, dots, apostrophes and hyphens")
    private String itemName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @PositiveOrZero(message = "Unit price must be 0 or greater")
    private double unitPrice;

    @PositiveOrZero(message = "Total amount must be 0 or greater")
    private double totalAmount;

    @Pattern(regexp = "^$|^(CREATED|APPROVED|RECEIVED|CANCELLED)$", message = "Status must be CREATED, APPROVED, RECEIVED or CANCELLED")
    private String status;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Public no-argument constructor required by JPA
    public PurchaseOrder() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
