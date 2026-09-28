package com.gokul.dronelocker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;

    private double allocatedAmount;

    private double actualExpense;

    private double actualRevenue;

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
