package com.example.realestate.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRequest {
    private Long leaseId;
    private BigDecimal amount;
    private LocalDate dueDate;

    // Конструкторы
    public PaymentRequest() {}

    public PaymentRequest(Long leaseId, BigDecimal amount, LocalDate dueDate) {
        this.leaseId = leaseId;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    // Геттеры и сеттеры
    public Long getLeaseId() { return leaseId; }
    public void setLeaseId(Long leaseId) { this.leaseId = leaseId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}