package com.example.realestate.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseRequest {
    private Long propertyId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlyRent;

    // Конструкторы
    public LeaseRequest() {}

    public LeaseRequest(Long propertyId, Long tenantId, LocalDate startDate, LocalDate endDate, BigDecimal monthlyRent) {
        this.propertyId = propertyId;
        this.tenantId = tenantId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
    }

    // Геттеры и сеттеры
    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(BigDecimal monthlyRent) { this.monthlyRent = monthlyRent; }
}