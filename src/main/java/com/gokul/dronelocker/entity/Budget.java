package com.gokul.dronelocker.entity;

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
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Zone name is required")
    @Size(min = 2, max = 100, message = "Zone name must be between 2 and 100 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9\\s,\\-]*$", message = "Zone name must start with a letter and contain only letters, numbers, spaces, commas and hyphens")
    private String zoneName;

    @PositiveOrZero(message = "Allocated amount must be 0 or greater")
    private double allocatedAmount;

    @PositiveOrZero(message = "Actual expense must be 0 or greater")
    private double actualExpense;

    @PositiveOrZero(message = "Actual revenue must be 0 or greater")
    private double actualRevenue;

    @NotBlank(message = "Financial year is required")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}$", message = "Financial year must be in format YYYY-YYYY (e.g. 2025-2026)")
    private String financialYear;

    // Public no-argument constructor required by JPA
    public Budget() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getActualExpense() {
        return actualExpense;
    }

    public void setActualExpense(double actualExpense) {
        this.actualExpense = actualExpense;
    }

    public double getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(double actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }
}
